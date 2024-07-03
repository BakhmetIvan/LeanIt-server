package mate.leanitserver.dto.grammar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class GrammarRequestDto {
    @NotBlank(message = "Title can't be blank")
    @Length(max = 255, message = "Title can't be longer than 255")
    private String title;
    private String imageUrl;
    @NotBlank(message = "Description can't be blank")
    private String description;
    @NotNull(message = "Video id can't be null")
    private Long videoId;
    @NotBlank(message = "Article text can't be blank")
    private String articleText;
    @NotBlank(message = "External Url can't be blank")
    private String href;
}
