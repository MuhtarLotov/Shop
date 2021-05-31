package team.alabs.wso3.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import team.alabs.wso3.converter.ProxyDtoConverter;
import team.alabs.wso3.entity.Proxy;
import team.alabs.wso3.entity.Role;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.GrantRoleDto;
import team.alabs.wso3.model.ProxyDto;
import team.alabs.wso3.model.ProxyUpdateDto;
import team.alabs.wso3.repository.ProxyRepository;
import team.alabs.wso3.service.CurrentUserService;
import team.alabs.wso3.service.ProxyExecutionService;
import team.alabs.wso3.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProxyExecutionServiceImpl implements ProxyExecutionService {

    private final ProxyRepository proxyRepository;
    private final RestTemplate restTemplate;
    private final CurrentUserService validationService;
    private final ProxyDtoConverter proxyDtoConverter;
    private final RoleService roleService;

    @Override
    public ResponseEntity<byte[]> execute(HttpServletRequest httpServletRequest, MultiValueMap<String, String> headers) throws IOException {
        Proxy proxyParams = getProxyParams(httpServletRequest.getServletPath());
        validateUserAuthority(proxyParams.getRoles());

        log.info(String.format("Current username: %s", validationService.getCurrentUserName()));

        String endpoint = getUrlPart(httpServletRequest.getServletPath());
        String url = proxyParams.getUrl() + endpoint;
        if (Objects.nonNull(httpServletRequest.getQueryString())) {
            url = url + "?" + httpServletRequest.getQueryString();
        }

        String method = httpServletRequest.getMethod();

        byte[] bytes = httpServletRequest.getInputStream().readAllBytes();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(bytes, headers);

        log.info(String.format("URL of request: %s [%s], body of request:\n%s",
                url, method, new String(bytes, StandardCharsets.UTF_8)));

        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, byte[].class);

        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, List<String>> item : responseEntity.getHeaders().entrySet()) {
            for (String value : item.getValue()) {
                if (HttpHeaders.TRANSFER_ENCODING.equals(item.getKey())) {
                    continue;
                }
                httpHeaders.add(item.getKey(), value);
            }
        }

        return ResponseEntity.status(responseEntity.getStatusCode()).headers(httpHeaders).body(responseEntity.getBody());
    }

    @Override
    public Integer grantRole(GrantRoleDto grantRoleDto) {
        Proxy proxy = proxyRepository.findById(grantRoleDto.getEntityId())
                .orElseThrow(() -> new ValidationException("no proxy service with id:%s", grantRoleDto.getEntityId()));


        boolean userHasRole = proxy.getRoles().stream().anyMatch(itm -> grantRoleDto.getRole().equals(itm.getRole()));
        if (userHasRole) {
            throw new ValidationException("proxy service: %s already has role %s", proxy.getService(), grantRoleDto.getRole());
        }

        Role role = new Role();
        role.setRole(grantRoleDto.getRole());
        proxy.getRoles().add(role);
        return proxyRepository.save(proxy).getId();
    }

    @Override
    @Transactional
    public ProxyDto createProxy(ProxyDto proxyDto) {
        Optional<Proxy> proxyByService = proxyRepository.findProxyByService(proxyDto.getService());
        proxyByService.ifPresent(itm -> {
            throw new ValidationException("service: %s already present", proxyDto.getService());
        });
        Proxy proxy = proxyDtoConverter.convertToEntity(proxyDto);
        proxyRepository.save(proxy);

        if (proxyDto.getRoles() != null && !proxyDto.getRoles().isEmpty()) {
            proxyDto.getRoles().forEach(role -> {
                GrantRoleDto grantRoleDto = new GrantRoleDto(proxy.getId(), role.getRole());
                grantRole(grantRoleDto);
            });
        }

        return proxyDtoConverter.convertToDto(proxy);
    }

    @Override
    public ProxyDto updateProxy(ProxyUpdateDto proxyUpdateDto) {
        Proxy proxy = validateAndGet(proxyUpdateDto.getId());

        proxyUpdateDto.getRoles().forEach(roleService::validateExists);

        proxy.setUrl(proxyUpdateDto.getUrl());
        proxy.setService(proxyUpdateDto.getService());

        proxy.setRoles(proxyUpdateDto.getRoles());

        proxyRepository.save(proxy);

        return proxyDtoConverter.convertToDto(proxy);
    }

    @Override
    public List<ProxyDto> getAll() {
        return proxyRepository.findAll().stream().map(proxyDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Integer delete(Integer id) {
        validateAndGet(id);
        proxyRepository.deleteById(id);
        return id;
    }

    private void validateUserAuthority(Set<Role> rolesSet) {
        Set<String> roles = rolesSet.stream().map(Role::getRole).collect(Collectors.toSet());
        validationService.validateUserHasAuthority(roles);
    }

    private Proxy getProxyParams(String servletPath) {
        String service = getUrlService(servletPath);


        return proxyRepository.findProxyByService(service)
                .orElseThrow(() -> new ValidationException("no service: %s", service));
    }

    private String getUrlPart(String contextPath) {
        StringBuilder builder = new StringBuilder("/");
        String[] split = contextPath.split("/");
        for (int i = 3; i < split.length; i++) {
            builder.append(split[i]).append("/");
        }
        if (builder.charAt(builder.length() - 1) == '/') {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    private String getUrlService(String servletPath) {
        return "/" + servletPath.split("/")[2];
    }

    public Proxy validateAndGet(Integer id) {
        return proxyRepository.findById(id)
                .orElseThrow(() -> new ValidationException("no proxy service with id: %s", id));

    }
}
