package com.leanitserver.dto.search;

import lombok.Data;

@Data
public class SearchResponseDto {
    private Long id;
    private String title;
    private String description;
    private String href;
    private String type;
    private String imageUrl;
}
