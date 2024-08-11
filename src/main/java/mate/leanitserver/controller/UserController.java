package mate.leanitserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.favorite.FavoriteRequestDto;
import mate.leanitserver.dto.resource.ResourceResponseDto;
import mate.leanitserver.dto.search.SearchResponseDto;
import mate.leanitserver.dto.user.UserResponseDto;
import mate.leanitserver.dto.user.UserUpdateImageDto;
import mate.leanitserver.dto.user.UserUpdateInfoDto;
import mate.leanitserver.dto.user.UserUpdatePasswordDto;
import mate.leanitserver.model.User;
import mate.leanitserver.service.FavoriteService;
import mate.leanitserver.service.ResourceService;
import mate.leanitserver.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "Profile management", description = "Endpoints for profile operations")
public class UserController {
    private final UserService userService;
    private final ResourceService resourceService;
    private final FavoriteService favoriteService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get user info",
            description = "Returns personal info about the current user")
    public UserResponseDto getInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.getInfo(user);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/my-resources")
    @Operation(summary = "Get user resources",
            description = "Returns page of a resources created by current user")
    public Page<ResourceResponseDto> getResources(Authentication authentication,
                                                  @PageableDefault Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return resourceService.findAllByUser(user, pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/favorites")
    @Operation(summary = "Get user favorites",
            description = "Returns page of a user favorites")
    public Page<SearchResponseDto> getFavorites(
            Authentication authentication,
            @NotBlank @Length(max = 255) String type,
            @PageableDefault Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return favoriteService.findAll(user, pageable, type);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/favorites")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add article to favorite",
            description = "Endpoint for adding article to favorite")
    public void addFavorite(Authentication authentication,
                                           @RequestBody @Valid FavoriteRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        favoriteService.addFavorite(user, requestDto);
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
    @PutMapping("/update-password")
    @Operation(summary = "Update user password",
            description = "Endpoint for update password for the current user")
    public UserResponseDto updatePassword(
            @RequestBody @Valid UserUpdatePasswordDto updatePasswordDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.updatePassword(user, updatePasswordDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/update-image")
    @Operation(summary = "Update user image",
            description = "Endpoint for update image for the current user")
    public UserResponseDto updateImage(@RequestBody @Valid UserUpdateImageDto updateImageDto,
                                          Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.updateImage(user, updateImageDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/favorite/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user favorite",
            description = "Endpoint for delete user`s favorite article")
    public void deleteFavorite(Authentication authentication,
                               @PathVariable @Positive Long id) {
        User user = (User) authentication.getPrincipal();
        favoriteService.delete(user, id);
    }
}
