package com.leanitserver.service.impl;

import com.leanitserver.dto.user.UserRegistrationDto;
import com.leanitserver.dto.user.UserResponseDto;
import com.leanitserver.dto.user.UserUpdateImageDto;
import com.leanitserver.dto.user.UserUpdateInfoDto;
import com.leanitserver.dto.user.UserUpdatePasswordDto;
import com.leanitserver.exception.EntityNotFoundException;
import com.leanitserver.exception.InvalidPasswordException;
import com.leanitserver.exception.RegistrationException;
import com.leanitserver.mapper.UserMapper;
import com.leanitserver.model.Role;
import com.leanitserver.model.User;
import com.leanitserver.repository.RoleRepository;
import com.leanitserver.repository.UserRepository;
import com.leanitserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_START_NAME = "User";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationDto requestDto) throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(String.format(
                    "Email already registered: %s", requestDto.getEmail())
            );
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(roleRepository.findByName(Role.RoleName.ROLE_USER).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "can't find role by name: %s", Role.RoleName.ROLE_USER)))
        );
        user = userRepository.save(user);
        user.setName(USER_START_NAME + user.getId());
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto getInfo(User user) {
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateInfo(User user, UserUpdateInfoDto userUpdateInfoDto) {
        userMapper.userUpdateInfoFromDto(user, userUpdateInfoDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updatePassword(User user, UserUpdatePasswordDto updatePasswordDto) {
        if (!passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateImage(User user, UserUpdateImageDto userUpdateImageDto) {
        userMapper.userUpdateImageFromDto(user, userUpdateImageDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
