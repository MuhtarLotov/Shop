package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.alabs.wso3.Constants;
import team.alabs.wso3.converter.UserCreateDtoConverter;
import team.alabs.wso3.converter.UserDtoConverter;
import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.entity.BuyAndSell;
import team.alabs.wso3.entity.Role;
import team.alabs.wso3.entity.User;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.GrantRoleDto;
import team.alabs.wso3.model.UserCreateDto;
import team.alabs.wso3.model.UserDto;
import team.alabs.wso3.model.UserUpdateDto;
import team.alabs.wso3.repository.BuyAndSellRepository;
import team.alabs.wso3.repository.UserRepository;
import team.alabs.wso3.service.RoleService;
import team.alabs.wso3.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserCreateDtoConverter userCreateDtoConverter;
    private final UserDtoConverter userDtoConverter;
    private final BuyAndSellRepository buyAndSellRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserByLogin(String userName) {
        return userRepository.findUserByLogin(userName);
    }

    @Override
    @Transactional
    public UserDto createUser(UserCreateDto userCreateDto) {
        Optional<User> userByLogin = userRepository.findUserByLogin(userCreateDto.getLogin());
        userByLogin.ifPresent(itm -> {
            throw new ValidationException("user with login: %s already exists", userCreateDto.getLogin());
        });
        User user = userCreateDtoConverter.convertToEntity(userCreateDto);
        userRepository.save(user);

        if (userCreateDto.getRoles() != null && !userCreateDto.getRoles().isEmpty()) {
            userCreateDto.getRoles().forEach(role -> {
                GrantRoleDto grantRoleDto = new GrantRoleDto(user.getId(), role.getRole());
                grantRole(grantRoleDto);
            });
        }

        return userDtoConverter.convertToDto(user);
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        if (Constants.ADMIN_USER_ID.equals(userUpdateDto.getId())) {
            throw new ValidationException("You can not delete base user with id: %s", userUpdateDto.getId());
        }
        userUpdateDto.getRoles().forEach(roleService::validateExists);

        User user = validateAndGet(userUpdateDto.getId());
        user.setPassword(userUpdateDto.getPassword());


        user.setRoles(userUpdateDto.getRoles());

        userRepository.save(user);
        return userDtoConverter.convertToDto(user);
    }

    @Override
    public Integer delete(Integer id) {
        if (Constants.ADMIN_USER_ID.equals(id)) {
            throw new ValidationException("You can not delete base user with id: %s", id);
        }
        User user = validateAndGet(id);
        userRepository.delete(user);
        return id;
    }

    @Override
    public Integer grantRole(GrantRoleDto grantRoleDto) {
        roleService.validateExists(grantRoleDto.getRole());
        User user = validateAndGet(grantRoleDto.getEntityId());

        boolean userHasRole = user.getRoles().stream().anyMatch(itm -> grantRoleDto.getRole().equals(itm.getRole()));
        if (userHasRole) {
            throw new ValidationException("user %s already has role %s", user.getLogin(), grantRoleDto.getRole());
        }

        Role role = new Role();
        role.setRole(grantRoleDto.getRole());
        user.getRoles().add(role);
        return userRepository.save(user).getId();
    }

    public User validateAndGet(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ValidationException("no user with id: %s", id));
    }
}
