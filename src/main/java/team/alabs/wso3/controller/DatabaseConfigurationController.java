package team.alabs.wso3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.alabs.wso3.Constants;
import team.alabs.wso3.model.DatabaseConfigurationDto;
import team.alabs.wso3.service.DatabaseConfigurationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/database-configuration")
public class DatabaseConfigurationController {

    private final DatabaseConfigurationService databaseConfigurationService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<DatabaseConfigurationDto> getAllDatabaseConfigurations() {
        return databaseConfigurationService.getAllDatabaseConfigurations();
    }

    @GetMapping("/test/{databaseConfigurationId}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public ResponseEntity testDatabaseConfiguration(@PathVariable Integer databaseConfigurationId) {
        if (databaseConfigurationService.testConnection(databaseConfigurationId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/batch")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<DatabaseConfigurationDto> createDatabaseConfiguration(@RequestBody List<DatabaseConfigurationDto> databaseConfigurationDtoList) {
        List<DatabaseConfigurationDto> result = new ArrayList<>();
        databaseConfigurationDtoList.forEach(databaseConfigurationDto -> result.add(databaseConfigurationService.createDatabaseConfiguration(databaseConfigurationDto)));
        return result;
    }

    @PostMapping
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public DatabaseConfigurationDto createDatabaseConfiguration(@RequestBody DatabaseConfigurationDto databaseConfigurationDto) {
        return databaseConfigurationService.createDatabaseConfiguration(databaseConfigurationDto);
    }

    @PutMapping
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public DatabaseConfigurationDto updateDatabaseConfiguration(@RequestBody DatabaseConfigurationDto databaseConfigurationDto) {
        return databaseConfigurationService.updateDatabaseConfiguration(databaseConfigurationDto);
    }

    @DeleteMapping("/{databaseConfigurationId}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public Integer deleteDatabaseConfiguration(@PathVariable Integer databaseConfigurationId) {
        return databaseConfigurationService.delete(databaseConfigurationId);
    }
}
