package team.alabs.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.alabs.shop.Constants;
import team.alabs.shop.model.GrantRoleDto;
import team.alabs.shop.model.UserCreateDto;
import team.alabs.shop.model.UserDto;
import team.alabs.shop.model.UserUpdateDto;
import team.alabs.shop.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/batch")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    @Transactional
    public List<UserDto> createUsers(@RequestBody List<UserCreateDto> userCreateDtoList) {
        List<UserDto> result = new ArrayList<>();
        userCreateDtoList.forEach(userCreateDto -> result.add(createUser(userCreateDto)));
        return result;
    }

    @PostMapping
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public UserDto createUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public UserDto updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }

    @PostMapping("/grant-role")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public Integer grantRole(@Valid @RequestBody GrantRoleDto grantRoleDto) {
        return userService.grantRole(grantRoleDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public Integer deleteUser(@PathVariable Integer id) {
        return userService.delete(id);
    }
}
