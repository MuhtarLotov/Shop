package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.wso3.Constants;
import team.alabs.wso3.entity.Role;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.repository.RoleRepository;
import team.alabs.wso3.service.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(String roleString) {
        roleRepository.findById(roleString).ifPresent(itm -> {
            throw new ValidationException("role: already exists", roleString);
        });
        Role role = new Role();
        role.setRole(roleString);
        roleRepository.save(role);
        return role;
    }

    @Override
    public void validateExists(String role) {
        roleRepository.findById(role)
                .orElseThrow(() -> new ValidationException("no role: %s", role));
    }

    public void validateExists(Role role) {
        roleRepository.findById(role.getRole())
                .orElseThrow(() -> new ValidationException("no role: %s", role));
    }

    @Override
    public String delete(String role) {
        if (Constants.ROLE_ADMIN.equals(role)) {
            throw new ValidationException("You can not delete base role: %s", role);
        }
        roleRepository.deleteById(role);
        return role;
    }
}
