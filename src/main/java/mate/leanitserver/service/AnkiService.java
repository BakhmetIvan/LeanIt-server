package mate.leanitserver.service;

import java.util.List;
import mate.leanitserver.dto.anki.internal.AnkiRequestDto;
import mate.leanitserver.dto.anki.internal.AnkiResponseDto;
import org.springframework.data.domain.Pageable;

public interface AnkiService {
    void addCardToDeck(Long id, String deckName);

    AnkiResponseDto save(AnkiRequestDto requestDto);

    AnkiResponseDto update(Long id, AnkiRequestDto requestDto);

    List<AnkiResponseDto> findAll(Pageable pageable);

    AnkiResponseDto findById(Long id);

    void delete(Long id);
}
