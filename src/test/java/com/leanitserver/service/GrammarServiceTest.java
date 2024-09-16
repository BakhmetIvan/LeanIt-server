package com.leanitserver.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.leanitserver.dto.grammar.GrammarFullResponseDto;
import com.leanitserver.dto.grammar.GrammarRequestDto;
import com.leanitserver.dto.grammar.GrammarShortResponseDto;
import com.leanitserver.exception.EntityNotFoundException;
import com.leanitserver.mapper.GrammarMapper;
import com.leanitserver.model.ArticleType;
import com.leanitserver.model.Grammar;
import com.leanitserver.repository.ArticleTypeRepository;
import com.leanitserver.repository.GrammarRepository;
import com.leanitserver.service.impl.GrammarServiceImpl;
import com.leanitserver.testdata.GrammarTestData;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class GrammarServiceTest {
    public static final String GRAMMAR_NOT_FOUND_EXCEPTION = "Can't find grammar by id = %d";
    @Mock
    private GrammarRepository grammarRepository;
    @Mock
    private GrammarMapper grammarMapper;
    @Mock
    private ArticleTypeRepository articleTypeRepository;
    @InjectMocks
    private GrammarServiceImpl grammarService;

    @Test
    @DisplayName("Save grammar with valid data should return GrammarFullResponseDto")
    public void saveGrammar_withValidData_ShouldReturnGrammarFullResponseDto() {
        GrammarRequestDto requestDto = GrammarTestData.createGrammarRequestDto();
        Grammar grammar = GrammarTestData.createDefaultGrammar();
        ArticleType grammarArticleType = new ArticleType();
        grammarArticleType.setName(ArticleType.ArticleName.GRAMMAR);
        GrammarFullResponseDto expectedGrammar = GrammarTestData.createGrammarFullResponseDto();

        when(grammarMapper.toModel(requestDto)).thenReturn(grammar);
        when(articleTypeRepository.findByName(ArticleType.ArticleName.GRAMMAR))
                .thenReturn(Optional.of(grammarArticleType));
        when(grammarRepository.save(grammar)).thenReturn(grammar);
        when(grammarMapper.toFullDto(grammar)).thenReturn(expectedGrammar);

        GrammarFullResponseDto actualGrammar = grammarService.save(requestDto);

        Assertions.assertNotNull(actualGrammar);
        Assertions.assertEquals(expectedGrammar, actualGrammar);
        verify(grammarMapper, times(1)).toModel(requestDto);
        verify(articleTypeRepository, times(1)).findByName(ArticleType.ArticleName.GRAMMAR);
        verify(grammarRepository, times(1)).save(grammar);
        verify(grammarMapper, times(1)).toFullDto(grammar);
    }

    @Test
    @DisplayName("Find grammar by id should return GrammarFullResponseDto")
    public void findById_withValidId_ShouldReturnGrammarFullResponseDto() {
        Long id = 1L;
        Grammar grammar = GrammarTestData.createDefaultGrammar();
        GrammarFullResponseDto expectedGrammar = GrammarTestData.createGrammarFullResponseDto();

        when(grammarRepository.findById(id)).thenReturn(Optional.of(grammar));
        when(grammarMapper.toFullDto(grammar)).thenReturn(expectedGrammar);

        GrammarFullResponseDto actualGrammar = grammarService.findById(id);

        Assertions.assertNotNull(actualGrammar);
        Assertions.assertEquals(expectedGrammar, actualGrammar);
        verify(grammarRepository, times(1)).findById(id);
        verify(grammarMapper, times(1)).toFullDto(grammar);
    }

    @Test
    @DisplayName("Find grammar by invalid id should throw EntityNotFoundException")
    public void findById_withInvalidId_ShouldThrowEntityNotFoundException() {
        Long id = 100L;

        when(grammarRepository.findById(id)).thenReturn(Optional.empty());

        String actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> grammarService.findById(id)).getMessage();
        String expected = String.format(GRAMMAR_NOT_FOUND_EXCEPTION, id);

        Assertions.assertEquals(expected, actual);
        verify(grammarRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Find all grammars should return Page of GrammarShortResponseDto")
    public void findAll_shouldReturnPageOfGrammarShortResponseDto() {
        Pageable pageable = Pageable.ofSize(10);
        Grammar grammar = GrammarTestData.createDefaultGrammar();
        GrammarShortResponseDto expectedGrammar = GrammarTestData.createGrammarShortResponseDto();
        Page<Grammar> grammarPage = new PageImpl<>(Collections.singletonList(grammar));

        when(grammarRepository.findAll(pageable)).thenReturn(grammarPage);
        when(grammarMapper.toShortDto(grammar)).thenReturn(expectedGrammar);

        Page<GrammarShortResponseDto> actualPage = grammarService.findAll(pageable);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(1, actualPage.getTotalElements());
        Assertions.assertEquals(expectedGrammar, actualPage.getContent().get(0));
        verify(grammarRepository, times(1)).findAll(pageable);
        verify(grammarMapper, times(1)).toShortDto(grammar);
    }

    @Test
    @DisplayName("Update grammar with valid data should return updated GrammarFullResponseDto")
    public void updateGrammar_withValidData_ShouldReturnUpdatedGrammarFullResponseDto() {
        Long id = 1L;
        GrammarRequestDto requestDto = GrammarTestData.createGrammarRequestDto();
        Grammar grammar = GrammarTestData.createDefaultGrammar();
        GrammarFullResponseDto expectedGrammar = GrammarTestData.createGrammarFullResponseDto();

        when(grammarRepository.findById(id)).thenReturn(Optional.of(grammar));
        when(grammarRepository.save(grammar)).thenReturn(grammar);
        when(grammarMapper.toFullDto(grammar)).thenReturn(expectedGrammar);

        GrammarFullResponseDto actualGrammar = grammarService.update(id, requestDto);

        Assertions.assertNotNull(actualGrammar);
        Assertions.assertEquals(expectedGrammar, actualGrammar);
        verify(grammarRepository, times(1)).findById(id);
        verify(grammarRepository, times(1)).save(grammar);
        verify(grammarMapper, times(1)).toFullDto(grammar);
    }

    @Test
    @DisplayName("Update grammar with invalid id should throw EntityNotFoundException")
    public void updateGrammar_withInvalidId_ShouldThrowEntityNotFoundException() {
        Long id = 100L;
        GrammarRequestDto requestDto = GrammarTestData.createGrammarRequestDto();

        when(grammarRepository.findById(id)).thenReturn(Optional.empty());

        String actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> grammarService.update(id, requestDto)).getMessage();
        String expected = String.format(GRAMMAR_NOT_FOUND_EXCEPTION, id);

        Assertions.assertEquals(expected, actual);
        verify(grammarRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Delete grammar by valid id should successfully delete grammar")
    public void deleteGrammar_withValidId_ShouldDeleteGrammar() {
        Long id = 1L;
        Grammar grammar = GrammarTestData.createDefaultGrammar();

        when(grammarRepository.findById(id)).thenReturn(Optional.of(grammar));

        grammarService.delete(id);

        verify(grammarRepository, times(1)).findById(id);
        verify(grammarRepository, times(1)).delete(grammar);
    }

    @Test
    @DisplayName("Delete grammar by invalid id should throw EntityNotFoundException")
    public void deleteGrammar_withInvalidId_ShouldThrowEntityNotFoundException() {
        Long id = 100L;

        when(grammarRepository.findById(id)).thenReturn(Optional.empty());

        String actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> grammarService.delete(id)).getMessage();
        String expected = String.format(GRAMMAR_NOT_FOUND_EXCEPTION, id);

        Assertions.assertEquals(expected, actual);
        verify(grammarRepository, times(1)).findById(id);
    }
}
