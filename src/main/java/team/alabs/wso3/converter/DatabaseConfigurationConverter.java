package team.alabs.wso3.converter;

import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.DatabaseConfiguration;
import team.alabs.wso3.model.DatabaseConfigurationDto;

@Component
public class DatabaseConfigurationConverter implements Converter<DatabaseConfiguration, DatabaseConfigurationDto> {

    @Override
    public DatabaseConfigurationDto convertToDto(DatabaseConfiguration databaseConfiguration) {
        DatabaseConfigurationDto dto = new DatabaseConfigurationDto();
        if (databaseConfiguration.getId() != null) {
            dto.setId(databaseConfiguration.getId());
        }
        dto.setConnectionString(databaseConfiguration.getConnectionString());
        dto.setUsername(databaseConfiguration.getUsername());
        dto.setPassword(databaseConfiguration.getPassword());
        return dto;
    }

    @Override
    public DatabaseConfiguration convertToEntity(DatabaseConfigurationDto databaseConfigurationDto) {
        DatabaseConfiguration entity = new DatabaseConfiguration();
        if (databaseConfigurationDto.getId() != null) {
            entity.setId(databaseConfigurationDto.getId());
        }
        entity.setConnectionString(databaseConfigurationDto.getConnectionString());
        entity.setUsername(databaseConfigurationDto.getUsername());
        entity.setPassword(databaseConfigurationDto.getPassword());
        return entity;
    }
}
