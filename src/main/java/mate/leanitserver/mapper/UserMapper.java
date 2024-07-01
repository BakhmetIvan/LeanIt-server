package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.user.UserRegistrationDto;
import mate.leanitserver.dto.user.UserResponseDto;
import mate.leanitserver.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationDto registrationDto);

    UserResponseDto toDto(User user);
}
