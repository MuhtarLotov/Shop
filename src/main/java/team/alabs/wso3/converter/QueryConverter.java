package team.alabs.wso3.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.DatabaseConfiguration;
import team.alabs.wso3.entity.Query;
import team.alabs.wso3.model.DatabaseConfigurationDto;
import team.alabs.wso3.model.QueryDto;

@Component
public class QueryConverter implements Converter<Query, QueryDto> {

    @Override
    public QueryDto convertToDto(Query query) {
        QueryDto queryDto = new QueryDto();
        queryDto.setId(query.getId());
        queryDto.setName(query.getName());
        queryDto.setSql(query.getSql());
        queryDto.setPageable(query.getPageable());
        return queryDto;
    }

    @Override
    public Query convertToEntity(QueryDto queryDto) {
        Query query = new Query();
        if (queryDto.getId() != null) {
            query.setId(queryDto.getId());
        }
        query.setName(queryDto.getName());
        query.setSql(queryDto.getSql());
        query.setPageable(queryDto.getPageable());
        return query;
    }
}
