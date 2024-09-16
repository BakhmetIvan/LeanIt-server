package com.leanitserver.dto.video;

import lombok.Data;

@Data
public class VideoShortResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private String type;
}
