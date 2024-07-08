package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.anki.internal.AnkiRequestDto;
import mate.leanitserver.dto.anki.internal.AnkiResponseDto;
import mate.leanitserver.model.AnkiCard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface AnkiMapper {
    AnkiCard toModel(AnkiRequestDto requestDto);

    AnkiResponseDto toDto(AnkiCard ankiCard);

    void ankiUpdateFromDto(@MappingTarget AnkiCard ankiCard, AnkiRequestDto requestDto);
}
