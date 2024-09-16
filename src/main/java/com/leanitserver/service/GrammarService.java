package com.leanitserver.service;

import com.leanitserver.dto.grammar.GrammarFullResponseDto;
import com.leanitserver.dto.grammar.GrammarRequestDto;
import com.leanitserver.dto.grammar.GrammarShortResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrammarService {
    Page<GrammarShortResponseDto> findAll(Pageable pageable);

    GrammarFullResponseDto findById(Long id);

    GrammarFullResponseDto save(GrammarRequestDto requestDto);

    GrammarFullResponseDto update(Long id, GrammarRequestDto requestDto);

    void delete(Long id);
}
