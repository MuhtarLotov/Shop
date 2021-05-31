package team.alabs.wso3.model;

import lombok.Data;
import team.alabs.wso3.entity.Role;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class ProxyDto {
    private Integer id;
    @NotEmpty
    private String service;
    @NotEmpty
    private String url;
    private Set<Role> roles;
}
