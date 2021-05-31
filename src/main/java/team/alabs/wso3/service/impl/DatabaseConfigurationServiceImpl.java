package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.wso3.converter.DatabaseConfigurationConverter;
import team.alabs.wso3.entity.DatabaseConfiguration;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.DatabaseConfigurationDto;
import team.alabs.wso3.repository.DatabaseConfigurationRepository;
import team.alabs.wso3.service.DatabaseConfigurationService;
import team.alabs.wso3.utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatabaseConfigurationServiceImpl implements DatabaseConfigurationService {
    private final DatabaseConfigurationRepository databaseConfigurationRepository;
    private final DatabaseConfigurationConverter databaseConfigurationConverter;
    private final DatabaseConnection databaseConnection;

    @Override
    public List<DatabaseConfigurationDto> getAllDatabaseConfigurations() {
        return databaseConfigurationRepository.findAll().stream()
                .map(databaseConfigurationConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Integer delete(Integer databaseConfigurationId) {
        DatabaseConfiguration databaseConfiguration = validateAndGet(databaseConfigurationId);
        databaseConfigurationRepository.delete(databaseConfiguration);
        return databaseConfigurationId;
    }

    @Override
    public DatabaseConfigurationDto createDatabaseConfiguration(DatabaseConfigurationDto databaseConfigurationDto) {
        if (databaseConfigurationRepository.existsByConnectionStringAndUsernameAndPassword(
                databaseConfigurationDto.getConnectionString().trim(),
                databaseConfigurationDto.getUsername().trim(),
                databaseConfigurationDto.getPassword().trim()
        )) {
            throw new ValidationException("database configuration already exists");
        }
        DatabaseConfiguration databaseConfiguration = databaseConfigurationConverter.convertToEntity(databaseConfigurationDto);
        databaseConfigurationRepository.save(databaseConfiguration);
        return databaseConfigurationConverter.convertToDto(databaseConfiguration);
    }

    @Override
    public DatabaseConfigurationDto updateDatabaseConfiguration(DatabaseConfigurationDto databaseConfigurationDto) {
        DatabaseConfiguration databaseConfiguration = validateAndGet(databaseConfigurationDto.getId());
        databaseConfiguration = databaseConfigurationConverter.convertToEntity(databaseConfigurationDto);
        databaseConfigurationRepository.save(databaseConfiguration);
        return databaseConfigurationConverter.convertToDto(databaseConfiguration);
    }

    @Override
    public boolean testConnection(Integer databaseConfigurationId) {
        DatabaseConfiguration databaseConfiguration = validateAndGet(databaseConfigurationId);
        try {
            return databaseConnection.getDatasource(databaseConfiguration).getConnection() != null;
        } catch (SQLException throwables) {
            throw new ValidationException("could not connect to database");
        }
    }

    @Override
    public DatabaseConfiguration validateAndGet(Integer id) {
        return databaseConfigurationRepository.findById(id)
                .orElseThrow(() -> new ValidationException("no database configuration with id: %s", id));
    }
}
