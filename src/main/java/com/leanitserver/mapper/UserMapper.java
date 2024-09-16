package com.leanitserver.mapper;

import com.leanitserver.config.MapperConfig;
import com.leanitserver.dto.user.UserRegistrationDto;
import com.leanitserver.dto.user.UserResponseDto;
import com.leanitserver.dto.user.UserUpdateImageDto;
import com.leanitserver.dto.user.UserUpdateInfoDto;
import com.leanitserver.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationDto registrationDto);

    UserResponseDto toDto(User user);

    void userUpdateInfoFromDto(@MappingTarget User user, UserUpdateInfoDto userUpdateInfoDto);

    void userUpdateImageFromDto(@MappingTarget User user, UserUpdateImageDto userUpdateImageDto);
}
