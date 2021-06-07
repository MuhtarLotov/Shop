package team.alabs.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.alabs.shop.Constants;
import team.alabs.shop.entity.Role;
import team.alabs.shop.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/batch")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    @Transactional
    public List<Role> createRoles(@RequestBody List<String> roles) {
        List<Role> result = new ArrayList<>();
        roles.forEach(s -> result.add(createRole(s)));
        return result;
    }

    @PostMapping
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public Role createRole(@RequestBody String role) {
        return roleService.createRole(role);
    }

    @DeleteMapping("/{role}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public String deleteRole(@PathVariable String role) {
        return roleService.delete(role);
    }


}
