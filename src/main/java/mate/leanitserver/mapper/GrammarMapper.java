package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.grammar.GrammarFullResponseDto;
import mate.leanitserver.dto.grammar.GrammarRelatedDto;
import mate.leanitserver.dto.grammar.GrammarShortResponseDto;
import mate.leanitserver.model.Grammar;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface GrammarMapper {
    GrammarShortResponseDto toShortDto(Grammar grammar);

    GrammarFullResponseDto toFullDto(Grammar grammar);

    GrammarRelatedDto toRelatedDto(Grammar grammar);
}
