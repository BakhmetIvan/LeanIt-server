package com.leanitserver.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.leanitserver.dto.search.SearchResponseDto;
import com.leanitserver.mapper.SearchMapper;
import com.leanitserver.model.Grammar;
import com.leanitserver.model.Resource;
import com.leanitserver.model.Video;
import com.leanitserver.repository.GrammarRepository;
import com.leanitserver.repository.ResourceRepository;
import com.leanitserver.repository.VideoRepository;
import com.leanitserver.service.impl.SearchServiceImpl;
import com.leanitserver.testdata.GrammarTestData;
import com.leanitserver.testdata.ResourceTestData;
import com.leanitserver.testdata.VideoTestData;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @Mock
    private GrammarRepository grammarRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private SearchMapper searchMapper;

    @InjectMocks
    private SearchServiceImpl searchService;

    @Test
    public void findAllByTitle_withValidTitle_ShouldReturnSortedAndLimitedSearchResponseDtoList() {
        String title = "Title";

        Grammar grammar = GrammarTestData.createDefaultGrammar();
        Video video = VideoTestData.createDefaultVideo();
        Resource resource = ResourceTestData.createDefaultResource();

        List<Grammar> grammarList = List.of(grammar);
        List<Video> videoList = List.of(video);
        List<Resource> resourceList = List.of(resource);

        when(grammarRepository.findAllByTitle(title)).thenReturn(grammarList);
        when(videoRepository.findAllByTitle(title)).thenReturn(videoList);
        when(resourceRepository.findAllByTitle(title)).thenReturn(resourceList);

        SearchResponseDto grammarDto = new SearchResponseDto();
        grammarDto.setTitle(grammar.getTitle());
        SearchResponseDto videoDto = new SearchResponseDto();
        videoDto.setTitle(video.getTitle());
        SearchResponseDto resourceDto = new SearchResponseDto();
        resourceDto.setTitle(resource.getTitle());

        when(searchMapper.toDto(grammar)).thenReturn(grammarDto);
        when(searchMapper.toDto(video)).thenReturn(videoDto);
        when(searchMapper.toDto(resource)).thenReturn(resourceDto);

        List<SearchResponseDto> actual = searchService.findAllByTitle(title, Pageable.ofSize(10));

        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(grammar.getTitle(), actual.get(0).getTitle());
        Assertions.assertEquals(resource.getTitle(), actual.get(1).getTitle());
        Assertions.assertEquals(video.getTitle(), actual.get(2).getTitle());

        verify(grammarRepository, times(1)).findAllByTitle(title);
        verify(videoRepository, times(1)).findAllByTitle(title);
        verify(resourceRepository, times(1)).findAllByTitle(title);
        verify(searchMapper, times(1)).toDto(grammar);
        verify(searchMapper, times(1)).toDto(video);
        verify(searchMapper, times(1)).toDto(resource);
    }

    @Test
    public void findAllByTitle_withEmptyResults_ShouldReturnEmptyList() {
        String title = "nonExistingTitle";
        Pageable pageable = PageRequest.of(0, 5);

        when(grammarRepository.findAllByTitle(title)).thenReturn(List.of());
        when(videoRepository.findAllByTitle(title)).thenReturn(List.of());
        when(resourceRepository.findAllByTitle(title)).thenReturn(List.of());

        List<SearchResponseDto> actual = searchService.findAllByTitle(title, pageable);
        Assertions.assertTrue(actual.isEmpty());

        verify(grammarRepository, times(1)).findAllByTitle(title);
        verify(videoRepository, times(1)).findAllByTitle(title);
        verify(resourceRepository, times(1)).findAllByTitle(title);
    }
}
