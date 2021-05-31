package team.alabs.wso3.service;

import team.alabs.wso3.entity.Data;
import team.alabs.wso3.entity.Query;
import team.alabs.wso3.model.QueryDto;

import java.util.List;

public interface QueryService {

    List<QueryDto> getQueriesByData(Data data);

    Integer deleteQuery(Integer id, Data data);

    QueryDto createQuery(QueryDto query, Data data);

    QueryDto updateQuery(QueryDto query, Data data);

    Query validateAndGetQuery(String name, Data data);
}
