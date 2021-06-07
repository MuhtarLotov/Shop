package team.alabs.shop.service;

import team.alabs.shop.entity.User;
import team.alabs.shop.model.GrantRoleDto;
import team.alabs.shop.model.UserCreateDto;
import team.alabs.shop.model.UserDto;
import team.alabs.shop.model.UserUpdateDto;

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
