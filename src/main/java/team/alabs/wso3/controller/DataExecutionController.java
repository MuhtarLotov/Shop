package team.alabs.wso3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.alabs.wso3.Constants;
import team.alabs.wso3.model.DataDto;
import team.alabs.wso3.model.DataUpdateDto;
import team.alabs.wso3.model.GrantRoleDto;
import team.alabs.wso3.service.DataExecutionService;
import team.alabs.wso3.service.DatabaseConfigurationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DataExecutionController {

    private final DataExecutionService dataExecutionService;
    private final DatabaseConfigurationService databaseConfigurationService;

    @RequestMapping("/data/**")
    public ResponseEntity<Object> execute(HttpServletRequest httpServletRequest) throws IOException {
        return ResponseEntity.ok(dataExecutionService.execute(httpServletRequest));
    }

    @PostMapping("/data-settings/grant-role")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public Integer grantRole(@Valid @RequestBody GrantRoleDto grantRoleDto) {
        return dataExecutionService.grantRole(grantRoleDto);
    }

    @GetMapping("/data-settings")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<DataDto> getAll() {
        return dataExecutionService.getAll();
    }

    @PostMapping("/data-settings/batch")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    @Transactional
    public List<DataDto> createData(@Valid @RequestBody List<DataDto> dataDtoList) {
        List<DataDto> result = new ArrayList<>();
        dataDtoList.forEach(dataDto -> {
            databaseConfigurationService.validateAndGet(dataDto.getDatabaseConfiguration().getId());
            result.add(dataExecutionService.createData(dataDto));
        });
        return result;
    }

    @PostMapping("/data-settings")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public DataDto createData(@Valid @RequestBody DataDto dataDto) {
        databaseConfigurationService.validateAndGet(dataDto.getDatabaseConfiguration().getId());
        return dataExecutionService.createData(dataDto);
    }

    @PutMapping("/data-settings/{id}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public DataDto updateData(@Valid @RequestBody DataUpdateDto dataUpdateDto) {
        return dataExecutionService.updateData(dataUpdateDto);
    }

    @DeleteMapping("/data-settings/{id}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public Integer delete(@PathVariable Integer id) {
        return dataExecutionService.delete(id);
    }
}
