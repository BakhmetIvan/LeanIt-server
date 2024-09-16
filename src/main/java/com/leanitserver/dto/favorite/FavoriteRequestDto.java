package com.leanitserver.dto.favorite;

import lombok.Data;

@Data
public class FavoriteRequestDto {
    private Long articleId;
    private String type;
}
