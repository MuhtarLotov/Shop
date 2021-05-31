package team.alabs.wso3.service;

import team.alabs.wso3.entity.DatabaseConfiguration;
import team.alabs.wso3.model.DatabaseConfigurationDto;

import java.util.List;

public interface DatabaseConfigurationService {

    List<DatabaseConfigurationDto> getAllDatabaseConfigurations();

    Integer delete(Integer databaseConfigurationId);

    DatabaseConfigurationDto createDatabaseConfiguration(DatabaseConfigurationDto databaseConfigurationDto);

    DatabaseConfigurationDto updateDatabaseConfiguration(DatabaseConfigurationDto databaseConfigurationDto);

    boolean testConnection(Integer databaseConfigurationId);

    DatabaseConfiguration validateAndGet(Integer id);
}
