package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.alabs.wso3.converter.DataDtoConverter;
import team.alabs.wso3.converter.QueryConverter;
import team.alabs.wso3.entity.Data;
import team.alabs.wso3.entity.Query;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.QueryDto;
import team.alabs.wso3.repository.QueryRepository;
import team.alabs.wso3.service.CurrentUserService;
import team.alabs.wso3.service.QueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
    private final QueryRepository queryRepository;
    private final QueryConverter queryConverter;
    private final CurrentUserService currentUserService;
    private final DataDtoConverter dataDtoConverter;

    @Override
    public List<QueryDto> getQueriesByData(Data data) {
        return queryRepository.findByData(data).stream().map(queryConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Integer deleteQuery(Integer id, Data data) {
        Query query = validateAndGetQuery(id, data);
        queryRepository.deleteByNameAndData(query.getName(), data);
        return id;
    }

    @Override
    public QueryDto createQuery(QueryDto queryDto, Data data) {
        validateSqlInjection(queryDto.getSql());

        Query query = queryConverter.convertToEntity(queryDto);
        query.setData(data);
        query = queryRepository.save(query);
        return queryConverter.convertToDto(query);
    }

    @Override
    public QueryDto updateQuery(QueryDto queryDto, Data data) {
        validateSqlInjection(queryDto.getSql());

        Query query = queryConverter.convertToEntity(queryDto);
        query.setData(data);
        query = queryRepository.save(query);
        return queryConverter.convertToDto(query);
    }

    @Override
    public Query validateAndGetQuery(String name, Data data) {
        return queryRepository.findByNameAndData(name, data)
                .orElseThrow(() -> new ValidationException("query with name %s does not exist", name));
    }

    public Query validateAndGetQuery(Integer id, Data data) {
        return queryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("query with id %s does not exist", id));
    }

    private void validateSqlInjection(String sql) {
        sql = sql.toUpperCase(Locale.ROOT);

        if (sql.contains("DELETE ") || sql.contains("UPDATE ") || sql.contains("INSERT ")) {
            throw new ValidationException("user: %s tried execute sql: %s injection",
                    currentUserService.getCurrentUserName(), sql);
        }
    }
}
