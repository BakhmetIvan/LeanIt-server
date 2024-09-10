package com.leanitserver.mapper;

import com.leanitserver.config.MapperConfig;
import com.leanitserver.dto.grammar.GrammarFullResponseDto;
import com.leanitserver.dto.grammar.GrammarRequestDto;
import com.leanitserver.dto.grammar.GrammarShortResponseDto;
import com.leanitserver.model.Grammar;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface GrammarMapper {
    GrammarShortResponseDto toShortDto(Grammar grammar);

    GrammarFullResponseDto toFullDto(Grammar grammar);

    Grammar toModel(GrammarRequestDto requestDto);

    void updateGrammarFromDto(@MappingTarget Grammar grammar, GrammarRequestDto requestDto);
}
