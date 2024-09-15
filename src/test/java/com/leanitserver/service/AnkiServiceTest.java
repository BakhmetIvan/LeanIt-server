package com.leanitserver.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.leanitserver.dto.anki.internal.AnkiRequestDto;
import com.leanitserver.dto.anki.internal.AnkiResponseDto;
import com.leanitserver.exception.EntityNotFoundException;
import com.leanitserver.mapper.AnkiMapper;
import com.leanitserver.model.AnkiCard;
import com.leanitserver.model.Video;
import com.leanitserver.repository.AnkiRepository;
import com.leanitserver.repository.VideoRepository;
import com.leanitserver.service.impl.AnkiServiceImpl;
import com.leanitserver.testdata.AnkiTestData;
import com.leanitserver.testdata.VideoTestData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class AnkiServiceTest {
    @Mock
    private AnkiRepository ankiRepository;
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private AnkiMapper ankiMapper;
    @InjectMocks
    private AnkiServiceImpl ankiService;

    @Test
    void save_shouldSaveAnkiCard_whenValidInput() {
        AnkiRequestDto requestDto = AnkiTestData.createAnkiRequestDto();
        AnkiCard ankiCard = AnkiTestData.createDefaultAnkiCard();
        Video video = VideoTestData.createDefaultVideo();
        List<AnkiCard> ankiCards = new ArrayList<>();
        video.setAnkiCards(ankiCards);

        when(videoRepository.findById(requestDto.getVideoId())).thenReturn(Optional.of(video));
        when(ankiMapper.toModel(requestDto)).thenReturn(ankiCard);
        when(ankiRepository.save(ankiCard)).thenReturn(ankiCard);
        when(videoRepository.save(video)).thenReturn(video);
        when(ankiMapper.toDto(ankiCard)).thenReturn(AnkiTestData.createAnkiResponseDto());

        AnkiResponseDto actual = ankiService.save(requestDto);

        Assertions.assertEquals(AnkiTestData.createAnkiResponseDto(), actual);
        verify(ankiRepository, times(1)).save(ankiCard);
        verify(videoRepository, times(1)).save(video);
        verify(ankiMapper, times(1)).toModel(requestDto);
        verify(ankiMapper, times(1)).toDto(ankiCard);
    }

    @Test
    void save_shouldThrowEntityNotFoundException_whenVideoNotFound() {
        AnkiRequestDto requestDto = AnkiTestData.createAnkiRequestDto();

        when(videoRepository.findById(requestDto.getVideoId())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> ankiService.save(requestDto));
    }

    @Test
    void update_shouldUpdateAnkiCard_whenValidInput() {
        Long id = 1L;
        AnkiRequestDto updateRequestDto = AnkiTestData.createUpdateAnkiRequestDto();
        AnkiCard ankiCard = AnkiTestData.createDefaultAnkiCard();
        AnkiResponseDto expectedAnki = AnkiTestData.createUpdatedAnkiResponseDto();

        when(ankiRepository.findById(id)).thenReturn(Optional.of(ankiCard));
        when(ankiRepository.save(ankiCard)).thenReturn(ankiCard);
        when(ankiMapper.toDto(ankiCard)).thenReturn(expectedAnki);

        AnkiResponseDto actual = ankiService.update(id, updateRequestDto);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedAnki, actual);
        verify(ankiRepository, times(1)).findById(id);
        verify(ankiRepository, times(1)).save(ankiCard);
        verify(ankiMapper, times(1)).toDto(ankiCard);
    }

    @Test
    void update_shouldThrowEntityNotFoundException_whenAnkiCardNotFound() {
        Long id = 1L;
        AnkiRequestDto requestDto = AnkiTestData.createUpdateAnkiRequestDto();

        when(ankiRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> ankiService.update(id, requestDto));
        verify(ankiRepository, times(1)).findById(id);
    }

    @Test
    void findAll_shouldReturnListOfAnkiCards() {
        List<AnkiCard> ankiCards = List.of(AnkiTestData.createDefaultAnkiCard());
        Page<AnkiCard> page = new PageImpl<>(ankiCards);

        when(ankiRepository.findAll(Pageable.ofSize(10))).thenReturn(page);
        when(ankiMapper.toDto(ankiCards.get(0))).thenReturn(AnkiTestData.createAnkiResponseDto());
        List<AnkiResponseDto> actual = ankiService.findAll(Pageable.ofSize(10));

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(AnkiTestData.createAnkiResponseDto(), actual.get(0));
        verify(ankiRepository, times(1)).findAll(Pageable.ofSize(10));
        verify(ankiMapper, times(1)).toDto(ankiCards.get(0));
    }

    @Test
    void findById_shouldReturnAnkiCard_whenFound() {
        Long id = 1L;
        AnkiCard ankiCard = AnkiTestData.createDefaultAnkiCard();
        AnkiResponseDto expectedAnki = AnkiTestData.createAnkiResponseDto();

        when(ankiRepository.findById(id)).thenReturn(Optional.of(ankiCard));
        when(ankiMapper.toDto(ankiCard)).thenReturn(expectedAnki);

        AnkiResponseDto actual = ankiService.findById(id);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedAnki, actual);
        verify(ankiRepository, times(1)).findById(id);
        verify(ankiMapper, times(1)).toDto(ankiCard);
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenNotFound() {
        Long id = 1L;

        when(ankiRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> ankiService.findById(id));
        verify(ankiRepository, times(1)).findById(id);
    }

    @Test
    void delete_shouldDeleteAnkiCard_whenFound() {
        Long id = 1L;
        AnkiCard ankiCard = AnkiTestData.createDefaultAnkiCard();

        when(ankiRepository.findById(id)).thenReturn(Optional.of(ankiCard));

        ankiService.delete(id);

        verify(ankiRepository).delete(ankiCard);
        verify(ankiRepository, times(1)).findById(id);
    }

    @Test
    void delete_shouldThrowEntityNotFoundException_whenNotFound() {
        Long id = 1L;

        when(ankiRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> ankiService.delete(id));
        verify(ankiRepository, times(1)).findById(id);
    }
}
