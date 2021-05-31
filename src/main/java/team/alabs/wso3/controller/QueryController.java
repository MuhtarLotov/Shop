package team.alabs.wso3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.alabs.wso3.entity.Data;
import team.alabs.wso3.model.QueryDto;
import team.alabs.wso3.service.DataExecutionService;
import team.alabs.wso3.service.QueryService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;
    private final DataExecutionService dataExecutionService;

    @GetMapping("/data-settings/{dataId}/query/all")
    public List<QueryDto> getAllDataQueries(@PathVariable Integer dataId) {
        Data data = dataExecutionService.validateAndGetData(dataId);
        return queryService.getQueriesByData(data);
    }

    @PostMapping("/data-settings/{dataId}/query/batch")
    @Transactional
    public List<QueryDto> createQuery(@RequestBody List<QueryDto> queryDtoList, @PathVariable Integer dataId) {
        Data data = dataExecutionService.validateAndGetData(dataId);
        List<QueryDto> result = new ArrayList<>();
        queryDtoList.forEach(queryDto -> result.add(queryService.createQuery(queryDto, data)));
        return result;
    }

    @PostMapping("/data-settings/{dataId}/query")
    public QueryDto createQuery(@RequestBody QueryDto queryDto, @PathVariable Integer dataId) {
        Data data = dataExecutionService.validateAndGetData(dataId);
        return queryService.createQuery(queryDto, data);
    }

    @PutMapping("/data-settings/{dataId}/query")
    public QueryDto updateQuery(@RequestBody QueryDto queryDto, @PathVariable Integer dataId) {
        Data data = dataExecutionService.validateAndGetData(dataId);
        return queryService.updateQuery(queryDto, data);
    }

    @DeleteMapping("/data-settings/{dataId}/query/{queryId}")
    public Integer deleteQuery(@PathVariable Integer dataId, @PathVariable Integer queryId) {
        Data data = dataExecutionService.validateAndGetData(dataId);
        return queryService.deleteQuery(queryId, data);
    }
}
