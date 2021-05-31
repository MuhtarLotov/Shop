package team.alabs.wso3.model;

import lombok.Data;
import team.alabs.wso3.entity.Role;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
public class DataDto {
    private Integer id;
    @NotEmpty
    private String service;
    private List<QueryDto> queryList;
    private DatabaseConfigurationDto databaseConfiguration;
    private Set<Role> roles;
}
