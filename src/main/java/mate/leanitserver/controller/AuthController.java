package mate.leanitserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.user.UserLoginDto;
import mate.leanitserver.dto.user.UserLoginResponseDto;
import mate.leanitserver.dto.user.UserRegistrationDto;
import mate.leanitserver.dto.user.UserResponseDto;
import mate.leanitserver.exception.RegistrationException;
import mate.leanitserver.security.AuthenticationService;
import mate.leanitserver.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication controller", description = "Endpoints for register and login for users")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "Register a user",
            description = "Register a new user if it is not exist in the db")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user",
            description = "Endpoint for logging in a user")
    public UserLoginResponseDto login(@RequestBody UserLoginDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
