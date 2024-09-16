package com.leanitserver.mapper;

import com.leanitserver.config.MapperConfig;
import com.leanitserver.dto.anki.internal.AnkiRequestDto;
import com.leanitserver.dto.anki.internal.AnkiResponseDto;
import com.leanitserver.model.AnkiCard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface AnkiMapper {
    AnkiCard toModel(AnkiRequestDto requestDto);

    AnkiResponseDto toDto(AnkiCard ankiCard);

    void ankiUpdateFromDto(@MappingTarget AnkiCard ankiCard, AnkiRequestDto requestDto);
}
