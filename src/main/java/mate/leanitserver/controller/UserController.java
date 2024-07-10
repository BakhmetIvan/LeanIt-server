package mate.leanitserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.user.UserResponseDto;
import mate.leanitserver.dto.user.UserUpdateImageDto;
import mate.leanitserver.dto.user.UserUpdateInfoDto;
import mate.leanitserver.dto.user.UserUpdatePasswordDto;
import mate.leanitserver.model.User;
import mate.leanitserver.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "Profile management", description = "Endpoints for profile operations")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get user info",
            description = "Returns personal info about the current user")
    public UserResponseDto getInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.getInfo(user);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/update-info")
    @Operation(summary = "Update user info",
            description = "Endpoint for update info for the current user")
    public UserResponseDto updateInfo(@RequestBody @Valid UserUpdateInfoDto updateInfoDto,
                                      Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.updateInfo(user, updateInfoDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PatchMapping("/update-password")
    @Operation(summary = "Update user password",
            description = "Endpoint for update password for the current user")
    public UserResponseDto updatePassword(
            @RequestBody @Valid UserUpdatePasswordDto updatePasswordDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.updatePassword(user, updatePasswordDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PatchMapping("/update-image")
    @Operation(summary = "Update user image",
            description = "Endpoint for update image for the current user")
    public UserResponseDto updateImage(@RequestBody @Valid UserUpdateImageDto updateImageDto,
                                          Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.updateImage(user, updateImageDto);
    }
}
