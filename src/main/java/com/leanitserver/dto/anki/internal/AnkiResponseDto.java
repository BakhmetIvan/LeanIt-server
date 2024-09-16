package com.leanitserver.dto.anki.internal;

import lombok.Data;

@Data
public class AnkiResponseDto {
    private Long id;
    private String imageUrl;
    private String front;
    private String back;
}
