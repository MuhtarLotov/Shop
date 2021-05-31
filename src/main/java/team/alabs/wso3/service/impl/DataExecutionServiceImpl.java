package team.alabs.wso3.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import team.alabs.wso3.Constants;
import team.alabs.wso3.converter.DataDtoConverter;
import team.alabs.wso3.entity.Data;
import team.alabs.wso3.entity.Query;
import team.alabs.wso3.entity.Role;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.DataDto;
import team.alabs.wso3.model.DataUpdateDto;
import team.alabs.wso3.model.GrantRoleDto;
import team.alabs.wso3.model.Page;
import team.alabs.wso3.repository.DataRepository;
import team.alabs.wso3.service.CurrentUserService;
import team.alabs.wso3.service.DataExecutionService;
import team.alabs.wso3.service.QueryService;
import team.alabs.wso3.service.RoleService;
import team.alabs.wso3.utils.DatabaseConnection;
import team.alabs.wso3.utils.PaginationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DataExecutionServiceImpl implements DataExecutionService {

    private final DataRepository dataRepository;
    private final CurrentUserService currentUserService;
    private final DataDtoConverter dataDtoConverter;
    private final RoleService roleService;
    private final DatabaseConnection databaseConnection;
    private final QueryService queryService;

    static final String REGEX_PATTERN = "@\\{(.*?)}";  // @{(param_name)}
    static final String STRING_REGEX_PATTERN = "'@\\{(.*?)}'"; // '@{(param_name)}'

    @Override
    public Object execute(HttpServletRequest httpServletRequest) {
        Data data = getDataService(httpServletRequest.getServletPath());
        validateUserAuthority(data.getRoles());

        String queryName = getQueryName(httpServletRequest.getServletPath());
        Query query = queryService.validateAndGetQuery(queryName, data);

        BasicDataSource dataSource = databaseConnection.getDatasource(data.getDatabaseConfiguration());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        String mainSql = evaluateParams(query.getSql(), parameterMap);

        if (query.getPageable()) {

            String totalSelect = String.format("select count(*) from (%s) AS count_info", mainSql);

            Integer totalElements = jdbcTemplate.queryForObject(totalSelect, Integer.class);
            totalElements = totalElements == null ? 0 : totalElements;
            int pageNumber = getIntOrDefault(parameterMap, "page", Constants.DEFAULT_PAGE, Integer.MAX_VALUE);
            int size = getIntOrDefault(parameterMap, "size", Constants.DEFAULT_CONTENT_SIZE, Constants.MAX_CONTENT_SIZE);
            String mainSqlWithPagination = PaginationUtil.addPagination(mainSql, pageNumber, size, PaginationUtil.DataBaseType.POSTGRES);
            List<Map<String, Object>> content = jdbcTemplate.queryForList(mainSqlWithPagination);
            databaseConnection.closeDatasource(dataSource);
            return Page.builder()
                    .totalElements(totalElements)
                    .totalPages(totalElements / size)
                    .page(pageNumber)
                    .size(size)
                    .content(content)
                    .build();
        }

        Object result = jdbcTemplate.queryForList(mainSql);
        databaseConnection.closeDatasource(dataSource);
        return result;
    }

    private int getIntOrDefault(Map<String, String[]> parameterMap, String paramName, int defaultPage, int max) {
        String[] param = parameterMap.get(paramName);
        if (ArrayUtils.isEmpty(param) || StringUtils.isEmpty(param[0])) {
            return defaultPage;
        }
        int val = Integer.parseInt(param[0]);
        return Math.min(val, max);
    }

    @Override
    public Integer grantRole(GrantRoleDto grantRoleDto) {
        roleService.validateExists(grantRoleDto.getRole());
        Data data = dataRepository.findById(grantRoleDto.getEntityId())
                .orElseThrow(() -> new ValidationException("no data service with id:%s", grantRoleDto.getEntityId()));


        boolean userHasRole = data.getRoles().stream().anyMatch(itm -> grantRoleDto.getRole().equals(itm.getRole()));
        if (userHasRole) {
            throw new ValidationException("data service already has role %s", grantRoleDto.getRole());
        }

        Role role = new Role();
        role.setRole(grantRoleDto.getRole());
        data.getRoles().add(role);
        return dataRepository.save(data).getId();
    }

    @Override
    @Transactional
    public DataDto createData(DataDto dataDto) {
        Optional<Data> dataByService = dataRepository.findDataByService(dataDto.getService());
        dataByService.ifPresent(itm -> {
            throw new ValidationException("service: %s already present", dataDto.getService());
        });

        Data data = dataDtoConverter.convertToEntity(dataDto);
        dataRepository.save(data);

        if (dataDto.getRoles() != null && !dataDto.getRoles().isEmpty()) {
            dataDto.getRoles().forEach(role -> {
                GrantRoleDto grantRoleDto = new GrantRoleDto(data.getId(), role.getRole());
                grantRole(grantRoleDto);
            });
        }

        return dataDtoConverter.convertToDto(data);
    }

    @Override
    public DataDto updateData(DataUpdateDto dataUpdateDto) {
        Data data = validateAndGetData(dataUpdateDto.getId());
        dataUpdateDto.getRoles().forEach(roleService::validateExists);

        data = dataDtoConverter.convertToEntity(dataUpdateDto);

        dataRepository.save(data);

        return dataDtoConverter.convertToDto(data);
    }

    @Override
    public List<DataDto> getAll() {
        return dataRepository.findAll().stream().map(dataDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Integer delete(Integer id) {
        validateAndGetData(id);
        dataRepository.deleteById(id);
        return id;
    }

    private static String evaluateParams(String sql, Map<String, String[]> parameterMap) {
        sql = evaluateParams(sql, parameterMap, STRING_REGEX_PATTERN);
        return evaluateParams(sql, parameterMap, REGEX_PATTERN);
    }

    private static String evaluateParams(String sql, Map<String, String[]> parameterMap, String regex) {
        Pattern getParamRegex = Pattern.compile(regex);
        Matcher matcher = getParamRegex.matcher(sql);
        return matcher.replaceAll(itm -> getParam(parameterMap, itm.group()));
    }

    private static String getParam(Map<String, String[]> parameterMap, String paramName) {
        boolean isStringParam = paramName.contains("'");
        paramName = paramName.replaceAll("[@{(')}]", "");
        String[] values = parameterMap.get(paramName);
        if (values != null && values.length > 0 && !StringUtils.isEmpty(values[0])) {
            return isStringParam ? String.format("'%s'", values[0]) : values[0];
        }
        return "null";
    }

    private Data getDataService(String servletPath) {
        String service = getServiceName(servletPath);
        return dataRepository.findDataByService(service)
                .orElseThrow(() -> new ValidationException("no service: %s", service));
    }

    private String getServiceName(String servletPath) {
        return "/" + servletPath.split("/")[2];
    }

    private String getQueryName(String servletPath) {
        return "/" + servletPath.split("/")[3];
    }


    private void validateUserAuthority(Set<Role> rolesSet) {
        if (!rolesSet.isEmpty()) {
            Set<String> roles = rolesSet.stream().map(Role::getRole).collect(Collectors.toSet());
            currentUserService.validateUserHasAuthority(roles);
        }
    }

    @Override
    public Data validateAndGetData(Integer id) {
        return dataRepository.findById(id)
                .orElseThrow(() -> new ValidationException("no data service with id: %s", id));
    }
}
