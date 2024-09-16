package com.leanitserver.dto.video;

import com.leanitserver.dto.anki.internal.AnkiResponseDto;
import java.util.List;
import lombok.Data;

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
