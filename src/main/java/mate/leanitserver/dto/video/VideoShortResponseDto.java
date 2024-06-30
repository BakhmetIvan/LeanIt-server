package mate.leanitserver.dto.video;

import lombok.Data;

@Data
public class VideoShortResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String content;
    private String type;
}
