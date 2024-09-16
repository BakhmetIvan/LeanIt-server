package com.leanitserver.service.impl;

import com.leanitserver.dto.grammar.GrammarFullResponseDto;
import com.leanitserver.dto.grammar.GrammarRequestDto;
import com.leanitserver.dto.grammar.GrammarShortResponseDto;
import com.leanitserver.exception.EntityNotFoundException;
import com.leanitserver.mapper.GrammarMapper;
import com.leanitserver.model.ArticleType;
import com.leanitserver.model.Grammar;
import com.leanitserver.repository.ArticleTypeRepository;
import com.leanitserver.repository.GrammarRepository;
import com.leanitserver.service.GrammarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrammarServiceImpl implements GrammarService {
    private static final String NOT_FOUND_GRAMMAR_EXCEPTION = "Can't find grammar by id = %d";
    private final GrammarRepository grammarRepository;
    private final GrammarMapper grammarMapper;
    private final ArticleTypeRepository articleTypeRepository;

    @Override
    public Page<GrammarShortResponseDto> findAll(Pageable pageable) {
        return grammarRepository.findAll(pageable)
                .map(grammarMapper::toShortDto);
    }

    @Override
    public GrammarFullResponseDto findById(Long id) {
        return grammarMapper.toFullDto(grammarRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_GRAMMAR_EXCEPTION, id)))
        );
    }

    @Override
    public GrammarFullResponseDto save(GrammarRequestDto requestDto) {
        Grammar grammar = grammarMapper.toModel(requestDto);
        grammar.setType(articleTypeRepository
                .findByName(ArticleType.ArticleName.GRAMMAR)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find article type"))
        );
        return grammarMapper.toFullDto(grammarRepository.save(grammar));
    }

    @Transactional
    @Override
    public GrammarFullResponseDto update(Long id, GrammarRequestDto requestDto) {
        Grammar grammar = grammarRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_GRAMMAR_EXCEPTION, id))
        );
        grammarMapper.updateGrammarFromDto(grammar, requestDto);
        return grammarMapper.toFullDto(grammarRepository.save(grammar));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Grammar grammar = grammarRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_GRAMMAR_EXCEPTION, id))
        );
        grammarRepository.delete(grammar);
    }
}
