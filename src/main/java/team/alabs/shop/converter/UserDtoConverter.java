package team.alabs.shop.converter;

import org.springframework.stereotype.Component;
import team.alabs.shop.entity.User;
import team.alabs.shop.exception.ValidationException;
import team.alabs.shop.model.UserDto;

@Component
public class UserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        throw new ValidationException("no implementation");
    }
}
