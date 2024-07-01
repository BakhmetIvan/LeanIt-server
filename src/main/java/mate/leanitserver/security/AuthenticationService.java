package mate.leanitserver.security;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.user.UserLoginDto;
import mate.leanitserver.dto.user.UserLoginResponseDto;
import mate.leanitserver.model.User;
import mate.leanitserver.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public UserLoginResponseDto authenticate(UserLoginDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                        requestDto.getPassword())
        );
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Can't find user by email: " + requestDto.getEmail()));
        String jwt = jwtUtil.generateToken(user);
        return new UserLoginResponseDto(jwt);
    }
}
