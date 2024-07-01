package mate.leanitserver.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginDto {
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Incorrect email structure")
    @Length(max = 255, message = "Email can't be longer than 255")
    private String email;
    @NotBlank(message = "Password can't be blank")
    @Length(min = 8, max = 150, message = "Password must be between 8 and 50 characters")
    @ToString.Exclude
    private String password;
}
