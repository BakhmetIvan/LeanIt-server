package com.leanitserver.security;

import com.leanitserver.dto.user.UserLoginDto;
import com.leanitserver.dto.user.UserLoginResponseDto;
import com.leanitserver.exception.EntityNotFoundException;
import com.leanitserver.exception.LoginException;
import com.leanitserver.mapper.UserMapper;
import com.leanitserver.model.User;
import com.leanitserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserLoginResponseDto authenticate(UserLoginDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Email not registered"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                            requestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new LoginException("Invalid password", e);
        } catch (AuthenticationException e) {
            throw new LoginException("Authentication failed", e);
        }
        String jwt = jwtUtil.generateToken(user);
        return new UserLoginResponseDto(jwt, userMapper.toDto(user));
    }
}
