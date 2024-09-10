package com.leanitserver.dto.resource;

import lombok.Data;

@Data
public class ResourceResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private String type;
    private String href;
}
