package team.alabs.wso3.service;

import team.alabs.wso3.entity.User;
import team.alabs.wso3.model.GrantRoleDto;
import team.alabs.wso3.model.UserCreateDto;
import team.alabs.wso3.model.UserDto;
import team.alabs.wso3.model.UserUpdateDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();

    Optional<User> findUserByLogin(String userName);

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto updateUser(UserUpdateDto userUpdateDto);

    Integer delete(Integer id);

    Integer grantRole(GrantRoleDto grantRoleDto);
}
