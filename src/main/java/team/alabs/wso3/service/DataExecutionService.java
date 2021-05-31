package team.alabs.wso3.service;

import team.alabs.wso3.entity.Data;
import team.alabs.wso3.model.DataDto;
import team.alabs.wso3.model.DataUpdateDto;
import team.alabs.wso3.model.GrantRoleDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface DataExecutionService {
    Object execute(HttpServletRequest headers) throws IOException;

    Integer grantRole(GrantRoleDto grantRoleDto);

    DataDto createData(DataDto dataDto);

    DataDto updateData(DataUpdateDto dataUpdateDto);

    Data validateAndGetData(Integer id);

    List<DataDto> getAll();

    Integer delete(Integer id);
}
