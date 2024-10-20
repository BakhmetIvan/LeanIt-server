package com.leanitserver.dto.user;

import com.leanitserver.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch.List({
        @FieldMatch(first = "newPassword",
                second = "repeatPassword",
                message = "Password mismatch")
})
public class UserUpdatePasswordDto {
    @NotBlank(message = "Password can't be blank")
    @Length(min = 8, max = 150, message = "Password must be between 8 and 50 characters")
    @ToString.Exclude
    private String oldPassword;
    @NotBlank(message = "New password can't be blank")
    @Length(min = 8, max = 150, message = "New password must be between 8 and 150 characters")
    @ToString.Exclude
    private String newPassword;
    @NotBlank(message = "Repeated password can't be blank")
    @Length(min = 8, max = 150, message = "Password must be between 8 and 150 characters")
    @ToString.Exclude
    private String repeatPassword;
}
