package team.alabs.wso3.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.Data;
import team.alabs.wso3.model.DataDto;
import team.alabs.wso3.model.DataUpdateDto;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataDtoConverter implements Converter<Data, DataDto> {
    private final DatabaseConfigurationConverter databaseConfigurationConverter;

    @Override
    public DataDto convertToDto(Data data) {

        DataDto dataDto = new DataDto();
        dataDto.setId(data.getId());
        dataDto.setService(data.getService());
        dataDto.setDatabaseConfiguration(databaseConfigurationConverter.convertToDto(data.getDatabaseConfiguration()));
        dataDto.setRoles(data.getRoles());
        return dataDto;
    }

    @Override
    public Data convertToEntity(DataDto dataDto) {
        Data data = new Data();
        if (dataDto.getId() != null) {
            data.setId(data.getId());
        }
        data.setService(dataDto.getService());
        data.setDatabaseConfiguration(databaseConfigurationConverter.convertToEntity(dataDto.getDatabaseConfiguration()));
        data.setRoles(new HashSet<>());
        return data;
    }

    public Data convertToEntity(DataUpdateDto dataDto) {
        Data data = new Data();
        if (dataDto.getId() != null) {
            data.setId(dataDto.getId());
        }
        data.setService(dataDto.getService());
        data.setDatabaseConfiguration(databaseConfigurationConverter.convertToEntity(dataDto.getDatabaseConfiguration()));

        data.setRoles(dataDto.getRoles());

        return data;
    }
}
