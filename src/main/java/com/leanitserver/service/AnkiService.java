package com.leanitserver.service;

import com.leanitserver.dto.anki.internal.AnkiRequestDto;
import com.leanitserver.dto.anki.internal.AnkiResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AnkiService {
    void addCardToDeck(Long id);

    AnkiResponseDto save(AnkiRequestDto requestDto);

    AnkiResponseDto update(Long id, AnkiRequestDto requestDto);

    List<AnkiResponseDto> findAll(Pageable pageable);

    AnkiResponseDto findById(Long id);

    void delete(Long id);
}
