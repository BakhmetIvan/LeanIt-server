package mate.leanitserver.dto.video;

import java.util.List;
import lombok.Data;
import mate.leanitserver.dto.anki.internal.AnkiResponseDto;

@Data
public class VideoFullResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String description;
    private String href;
    private List<AnkiResponseDto> ankiCards;
}
