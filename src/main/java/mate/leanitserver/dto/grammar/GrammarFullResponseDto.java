package mate.leanitserver.dto.grammar;

import java.util.List;
import lombok.Data;

@Data
public class GrammarFullResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String description;
    private String mainSubTitle;
    private String secondTitle;
    private String thirdTitle;
    private String thirdSubTitle;
    private String fourthTitle;
    private String fourthSubTitle;
    private List<String> underTitleList;
    private String href;
}
