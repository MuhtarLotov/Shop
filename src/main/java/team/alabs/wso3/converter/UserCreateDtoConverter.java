package team.alabs.wso3.converter;

import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.User;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.UserCreateDto;

import java.util.HashSet;

@Component
public class UserCreateDtoConverter implements Converter<User, UserCreateDto> {

    @Override
    public UserCreateDto convertToDto(User user) {
        throw new ValidationException("no implementation");
    }

    @Override
    public User convertToEntity(UserCreateDto userCreateDto) {
        return User.builder()
                .login(userCreateDto.getLogin())
                .password(userCreateDto.getPassword())
                .roles(new HashSet<>())
                .build();
    }
}
