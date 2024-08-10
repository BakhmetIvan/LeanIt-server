package mate.leanitserver.dto.grammar;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
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
    private String videoUrl;
    @NotBlank(message = "Main sub title text can't be blank")
    private String mainSubTitle;
    private String secondTitle;
    private String thirdTitle;
    private String thirdSubTitle;
    private String fourthTitle;
    private String fourthSubTitle;
    private List<String> underTitleList;
    @NotBlank(message = "External Url can't be blank")
    private String href;
}
