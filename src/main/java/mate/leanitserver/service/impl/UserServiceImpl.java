package mate.leanitserver.service.impl;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.user.UserRegistrationDto;
import mate.leanitserver.dto.user.UserResponseDto;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.exception.RegistrationException;
import mate.leanitserver.mapper.UserMapper;
import mate.leanitserver.model.Role;
import mate.leanitserver.model.User;
import mate.leanitserver.repository.RoleRepository;
import mate.leanitserver.repository.UserRepository;
import mate.leanitserver.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
        return userMapper.toDto(userRepository.save(user));
    }
}
