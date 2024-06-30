package mate.leanitserver.dto.grammar;

import lombok.Data;

@Data
public class GrammarShortResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private String type;
}
