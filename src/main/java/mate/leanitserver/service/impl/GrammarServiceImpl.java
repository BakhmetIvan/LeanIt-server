package mate.leanitserver.service.impl;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.grammar.GrammarFullResponseDto;
import mate.leanitserver.dto.grammar.GrammarShortResponseDto;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.mapper.GrammarMapper;
import mate.leanitserver.repository.GrammarRepository;
import mate.leanitserver.service.GrammarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrammarServiceImpl implements GrammarService {
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
                        "Can't find grammar by id = %d", id)))
        );
    }
}
