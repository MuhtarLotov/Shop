package team.alabs.wso3.model;

import lombok.Builder;
import lombok.Data;
import team.alabs.wso3.entity.Role;

import java.util.Set;

@Data
@Builder
public class UserDto {

    private Integer id;
    private String login;
    private Set<Role> roles;
}
