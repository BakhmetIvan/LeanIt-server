package mate.leanitserver.security;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.user.UserLoginDto;
import mate.leanitserver.dto.user.UserLoginResponseDto;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.exception.LoginException;
import mate.leanitserver.mapper.UserMapper;
import mate.leanitserver.model.User;
import mate.leanitserver.repository.UserRepository;
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
