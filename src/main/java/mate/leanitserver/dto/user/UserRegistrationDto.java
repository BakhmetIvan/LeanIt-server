package mate.leanitserver.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import mate.leanitserver.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch.List({
        @FieldMatch(first = "password",
                second = "repeatPassword",
                message = "Password mismatch")
})
public class UserRegistrationDto {
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Incorrect email structure")
    @Length(max = 255, message = "Email can't be longer than 255")
    private String email;
    @NotBlank(message = "Password can't be blank")
    @Length(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @ToString.Exclude
    private String password;
    @NotBlank(message = "Repeated password can't be blank")
    @Length(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @ToString.Exclude
    private String repeatPassword;
}
