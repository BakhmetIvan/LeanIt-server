package com.leanitserver.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.leanitserver.dto.user.UserRegistrationDto;
import com.leanitserver.dto.user.UserResponseDto;
import com.leanitserver.dto.user.UserUpdateInfoDto;
import com.leanitserver.dto.user.UserUpdatePasswordDto;
import com.leanitserver.exception.InvalidPasswordException;
import com.leanitserver.exception.RegistrationException;
import com.leanitserver.mapper.UserMapper;
import com.leanitserver.model.Role;
import com.leanitserver.model.User;
import com.leanitserver.repository.RoleRepository;
import com.leanitserver.repository.UserRepository;
import com.leanitserver.service.impl.UserServiceImpl;
import com.leanitserver.testdata.UserTestData;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Register a new user with valid data")
    void register_withValidData_shouldRegisterNewUser() throws RegistrationException {
        UserRegistrationDto requestDto = UserTestData.createValidRegistrationDto();
        Role userRole = UserTestData.createUserRole();
        User user = UserTestData.createDefaultUser();
        UserResponseDto expectUser = UserTestData.createUserResponseDto();

        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName(Role.RoleName.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");
        when(userMapper.toModel(requestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectUser);

        UserResponseDto result = userService.register(requestDto);

        Assertions.assertEquals(expectUser, result);
        verify(userRepository, times(1)).findByEmail(requestDto.getEmail());
        verify(roleRepository, times(1)).findByName(Role.RoleName.ROLE_USER);
        verify(passwordEncoder, times(1)).encode(requestDto.getPassword());
        verify(userMapper, times(1)).toModel(requestDto);
        verify(userRepository, times(2)).save(user);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    @DisplayName("Register new user when email already exist "
            + "should throw the RegistrationException")
    void register_whenEmailAlreadyExists_shouldThrowRegistrationException() {
        UserRegistrationDto requestDto = UserTestData.createValidRegistrationDto();
        when(userRepository.findByEmail(requestDto.getEmail()))
                .thenReturn(Optional.of(new User()));

        Assertions.assertThrows(RegistrationException.class,
                () -> userService.register(requestDto));
        verify(userRepository, times(1))
                .findByEmail(requestDto.getEmail());
    }

    @Test
    @DisplayName("Update info with valid input data should update the user")
    void updateInfo_whenValidInput_shouldUpdateUserInfo() {
        User user = UserTestData.createUserWithEncodedPassword();
        UserUpdateInfoDto updateInfoDto = UserTestData.createValidUpdateInfoDto();
        UserResponseDto expectedUser = UserTestData.createUpdatedUserResponseDto();
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedUser);

        UserResponseDto actual = userService.updateInfo(user, updateInfoDto);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedUser, actual);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    @DisplayName("Update password when passwords dont match should throw invalidPasswordException")
    void updatePassword_whenOldPasswordDoesNotMatch_shouldThrowInvalidPasswordException() {
        User user = UserTestData.createUserWithEncodedPassword();
        UserUpdatePasswordDto updatePasswordDto = UserTestData.createValidUpdatePasswordDto();

        when(passwordEncoder.matches(updatePasswordDto.getOldPassword(),
                user.getPassword())).thenReturn(false);

        Assertions.assertThrows(InvalidPasswordException.class,
                () -> userService.updatePassword(user, updatePasswordDto));
        verify(passwordEncoder, times(1))
                .matches(updatePasswordDto.getOldPassword(), user.getPassword());
    }
}
