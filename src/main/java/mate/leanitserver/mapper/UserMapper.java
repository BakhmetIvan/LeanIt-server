package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.user.UserRegistrationDto;
import mate.leanitserver.dto.user.UserResponseDto;
import mate.leanitserver.dto.user.UserUpdateImageDto;
import mate.leanitserver.dto.user.UserUpdateInfoDto;
import mate.leanitserver.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationDto registrationDto);

    UserResponseDto toDto(User user);

    void userUpdateInfoFromDto(@MappingTarget User user, UserUpdateInfoDto userUpdateInfoDto);

    void userUpdateImageFromDto(@MappingTarget User user, UserUpdateImageDto userUpdateImageDto);
}
