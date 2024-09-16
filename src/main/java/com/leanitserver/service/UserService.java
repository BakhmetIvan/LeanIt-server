package com.leanitserver.service;

import com.leanitserver.dto.user.UserRegistrationDto;
import com.leanitserver.dto.user.UserResponseDto;
import com.leanitserver.dto.user.UserUpdateImageDto;
import com.leanitserver.dto.user.UserUpdateInfoDto;
import com.leanitserver.dto.user.UserUpdatePasswordDto;
import com.leanitserver.exception.RegistrationException;
import com.leanitserver.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationDto requestDto) throws RegistrationException;

    UserResponseDto getInfo(User user);

    UserResponseDto updateInfo(User user, UserUpdateInfoDto userUpdateInfoDto);

    UserResponseDto updatePassword(User user, UserUpdatePasswordDto updatePasswordDto);

    UserResponseDto updateImage(User user, UserUpdateImageDto userUpdateImageDto);
}
