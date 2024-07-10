package mate.leanitserver.service;

import mate.leanitserver.dto.user.UserRegistrationDto;
import mate.leanitserver.dto.user.UserResponseDto;
import mate.leanitserver.dto.user.UserUpdateImageDto;
import mate.leanitserver.dto.user.UserUpdateInfoDto;
import mate.leanitserver.dto.user.UserUpdatePasswordDto;
import mate.leanitserver.exception.RegistrationException;
import mate.leanitserver.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationDto requestDto) throws RegistrationException;

    UserResponseDto getInfo(User user);

    UserResponseDto updateInfo(User user, UserUpdateInfoDto userUpdateInfoDto);

    UserResponseDto updatePassword(User user, UserUpdatePasswordDto updatePasswordDto);

    UserResponseDto updateImage(User user, UserUpdateImageDto userUpdateImageDto);
}
