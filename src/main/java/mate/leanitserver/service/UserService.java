package mate.leanitserver.service;

import mate.leanitserver.dto.user.UserRegistrationDto;
import mate.leanitserver.dto.user.UserResponseDto;
import mate.leanitserver.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationDto requestDto) throws RegistrationException;
}
