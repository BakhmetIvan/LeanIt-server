package mate.leanitserver.dto.grammar;

import java.util.List;
import lombok.Data;
import mate.leanitserver.model.Video;

@Data
public class GrammarFullResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private Video video;
    private String content;
    private String externalUrl;
    private List<GrammarRelatedDto> relatedTopics;
}
