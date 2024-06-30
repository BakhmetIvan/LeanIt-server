package mate.leanitserver.dto.video;

import java.util.List;
import lombok.Data;

@Data
public class VideoFullResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String content;
    private String externalUrl;
    private List<VideoRelatedDto> relatedTopics;
}
