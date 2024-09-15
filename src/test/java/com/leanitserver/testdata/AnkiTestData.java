package com.leanitserver.testdata;

import com.leanitserver.dto.anki.internal.AnkiRequestDto;
import com.leanitserver.dto.anki.internal.AnkiResponseDto;
import com.leanitserver.model.AnkiCard;

public class AnkiTestData {
    public static AnkiCard createDefaultAnkiCard() {
        AnkiCard ankiCard = new AnkiCard();
        ankiCard.setId(1L);
        ankiCard.setImageUrl("Anki card image url");
        ankiCard.setFront("Anki card front");
        ankiCard.setBack("Anki card back");
        ankiCard.setDeleted(false);
        return ankiCard;
    }

    public static AnkiRequestDto createAnkiRequestDto() {
        AnkiRequestDto requestDto = new AnkiRequestDto();
        requestDto.setFront("Anki card front");
        requestDto.setBack("Anki card back");
        requestDto.setImageUrl("Anki card image url");
        requestDto.setVideoId(1L);
        return requestDto;
    }

    public static AnkiResponseDto createAnkiResponseDto() {
        AnkiResponseDto responseDto = new AnkiResponseDto();
        responseDto.setId(1L);
        responseDto.setImageUrl("Anki card image url");
        responseDto.setFront("Anki card front");
        responseDto.setBack("Anki card back");
        return responseDto;
    }

    public static AnkiRequestDto createUpdateAnkiRequestDto() {
        AnkiRequestDto updateDto = createAnkiRequestDto();
        updateDto.setFront("Updated front");
        return updateDto;
    }

    public static AnkiCard createUpdatedAnkiCard() {
        AnkiCard updatedAnki = createDefaultAnkiCard();
        updatedAnki.setFront("Updated front");
        return updatedAnki;
    }

    public static AnkiResponseDto createUpdatedAnkiResponseDto() {
        AnkiResponseDto updatedAnkiResponse = createAnkiResponseDto();
        updatedAnkiResponse.setFront("Updated front");
        return updatedAnkiResponse;
    }
}
