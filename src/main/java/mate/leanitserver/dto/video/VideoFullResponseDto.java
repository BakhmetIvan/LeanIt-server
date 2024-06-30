package mate.leanitserver.dto.video;

import lombok.Data;

@Data
public class VideoFullResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String description;
    private String href;
}
