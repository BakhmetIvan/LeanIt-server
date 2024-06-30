package mate.leanitserver.dto.grammar;

import lombok.Data;

@Data
public class GrammarFullResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private Long videoId;
    private String description;
    private String articleText;
    private String href;
}
