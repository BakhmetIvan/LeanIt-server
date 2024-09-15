package com.leanitserver.testdata;

import com.leanitserver.dto.user.UserRegistrationDto;
import com.leanitserver.dto.user.UserResponseDto;
import com.leanitserver.dto.user.UserUpdateInfoDto;
import com.leanitserver.dto.user.UserUpdatePasswordDto;
import com.leanitserver.model.Role;
import com.leanitserver.model.User;

public class UserTestData {
    public static User createDefaultUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("john@gmail.com");
        user.setPassword("johnpass");
        user.setImageId(1);
        user.setRole(createUserRole());
        user.setDeleted(false);
        return user;
    }

    public static Role createUserRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName(Role.RoleName.ROLE_USER);
        return role;
    }

    public static User createUserWithEncodedPassword() {
        User user = new User();
        user.setId(1L);
        user.setEmail("john@gmail.com");
        user.setPassword("encodedPassword");
        user.setImageId(1);
        user.setRole(createUserRole());
        user.setDeleted(false);
        return user;
    }

    public static UserRegistrationDto createValidRegistrationDto() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("john@gmail.com");
        dto.setPassword("johnpass");
        dto.setRepeatPassword("johnpass");
        return dto;
    }

    public static UserResponseDto createUserResponseDto() {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setEmail("john@gmail.com");
        return responseDto;
    }

    public static UserResponseDto createUpdatedUserResponseDto() {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setEmail("updated@gmail.com");
        responseDto.setName("UpdatedUser");
        return responseDto;
    }

    public static UserUpdateInfoDto createValidUpdateInfoDto() {
        UserUpdateInfoDto dto = new UserUpdateInfoDto();
        dto.setEmail("updated@gmail.com");
        dto.setName("UpdatedUser");
        return dto;
    }

    public static UserUpdatePasswordDto createValidUpdatePasswordDto() {
        UserUpdatePasswordDto dto = new UserUpdatePasswordDto();
        dto.setOldPassword("johnpass");
        dto.setNewPassword("newPassword");
        dto.setRepeatPassword("newPassword");
        return dto;
    }
}
