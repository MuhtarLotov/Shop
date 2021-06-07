package team.alabs.shop.service;

import team.alabs.shop.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    String delete(String role);

    Role createRole(String role);

    void validateExists(String role);

    void validateExists(Role role);
}
