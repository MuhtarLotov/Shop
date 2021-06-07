package team.alabs.shop.model;

import lombok.Builder;
import lombok.Data;
import team.alabs.shop.entity.Role;

import java.util.Set;

@Data
@Builder
public class UserDto {

    private Integer id;
    private String login;
    private Set<Role> roles;
}
