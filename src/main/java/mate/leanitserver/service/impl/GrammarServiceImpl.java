package mate.leanitserver.service.impl;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.grammar.GrammarFullResponseDto;
import mate.leanitserver.dto.grammar.GrammarRequestDto;
import mate.leanitserver.dto.grammar.GrammarShortResponseDto;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.mapper.GrammarMapper;
import mate.leanitserver.model.Grammar;
import mate.leanitserver.repository.GrammarRepository;
import mate.leanitserver.service.GrammarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrammarServiceImpl implements GrammarService {
    private static final String NOT_FOUND_GRAMMAR_EXCEPTION = "Can't find grammar by id = %d";
    private final GrammarRepository grammarRepository;
    private final GrammarMapper grammarMapper;

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
        return grammarMapper.toFullDto(grammarRepository.save(grammar));
    }

    @Override
    public GrammarFullResponseDto update(Long id, GrammarRequestDto requestDto) {
        Grammar grammar = grammarRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_GRAMMAR_EXCEPTION, id))
        );
        grammarMapper.updateGrammarFromDto(grammar, requestDto);
        return grammarMapper.toFullDto(grammarRepository.save(grammar));
    }

    @Override
    public void delete(Long id) {
        Grammar grammar = grammarRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_GRAMMAR_EXCEPTION, id))
        );
        grammarRepository.delete(grammar);
    }
}
