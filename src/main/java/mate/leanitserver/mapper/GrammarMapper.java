package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.grammar.GrammarFullResponseDto;
import mate.leanitserver.dto.grammar.GrammarRequestDto;
import mate.leanitserver.dto.grammar.GrammarShortResponseDto;
import mate.leanitserver.model.Grammar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface GrammarMapper {
    GrammarShortResponseDto toShortDto(Grammar grammar);

    @Mapping(target = "videoId", source = "video.id")
    GrammarFullResponseDto toFullDto(Grammar grammar);

    Grammar toModel(GrammarRequestDto requestDto);

    void updateGrammarFromDto(@MappingTarget Grammar grammar, GrammarRequestDto requestDto);
}
