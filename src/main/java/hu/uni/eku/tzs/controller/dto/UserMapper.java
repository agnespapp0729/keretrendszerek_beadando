package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userTouserDto(User user);

    User userDtoTouser(UserDto userDto);
}
