package mate.leanitserver.dto.favorite;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class FavoriteTypeRequestDto {
    @NotBlank(message = "Article type can't be blank")
    @Length(max = 255, message = "Type size can't be longer than 255")
    private String type;
}
