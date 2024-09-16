package com.leanitserver.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.leanitserver.dto.video.VideoFullResponseDto;
import com.leanitserver.dto.video.VideoRequestDto;
import com.leanitserver.dto.video.VideoShortResponseDto;
import com.leanitserver.exception.EntityNotFoundException;
import com.leanitserver.mapper.VideoMapper;
import com.leanitserver.model.ArticleType;
import com.leanitserver.model.Video;
import com.leanitserver.repository.ArticleTypeRepository;
import com.leanitserver.repository.VideoRepository;
import com.leanitserver.service.impl.VideoServiceImpl;
import com.leanitserver.testdata.ArticleTestData;
import com.leanitserver.testdata.VideoTestData;
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
public class VideoServiceTest {
    public static final String VIDEO_NOT_FOUND_EXCEPTION = "Can't find video by id = %d";
    @Mock
    private VideoMapper videoMapper;
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private ArticleTypeRepository articleTypeRepository;
    @InjectMocks
    private VideoServiceImpl videoService;

    @Test
    public void saveVideo_withValidData_ShouldReturnVideoFullResponseDto() {
        VideoRequestDto requestDto = VideoTestData.createVideoRequestDto();
        Video video = VideoTestData.createDefaultVideo();
        VideoFullResponseDto expectedVideo = VideoTestData.createVideoFullResponseDto();
        ArticleType videoArticleType = ArticleTestData.createVideoArticleType();

        when(videoMapper.toModel(requestDto)).thenReturn(video);
        when(articleTypeRepository.findByName(ArticleType.ArticleName.VIDEO))
                .thenReturn(Optional.of(videoArticleType));
        when(videoRepository.save(video)).thenReturn(video);
        when(videoMapper.toFullDto(video)).thenReturn(expectedVideo);

        VideoFullResponseDto actualVideo = videoService.save(requestDto);

        Assertions.assertNotNull(actualVideo);
        Assertions.assertEquals(expectedVideo, actualVideo);
        verify(videoMapper, times(1)).toModel(requestDto);
        verify(articleTypeRepository, times(1)).findByName(ArticleType.ArticleName.VIDEO);
        verify(videoRepository, times(1)).save(video);
        verify(videoMapper, times(1)).toFullDto(video);
    }

    @Test
    @DisplayName("Find video by id should return valid VideoFullResponseDto")
    public void findById_withValidId_ShouldReturnVideoFullResponseDto() {
        Long id = 1L;
        Video video = VideoTestData.createDefaultVideo();
        VideoFullResponseDto expectedVideo = VideoTestData.createVideoFullResponseDto();

        when(videoRepository.findById(id)).thenReturn(Optional.of(video));
        when(videoMapper.toFullDto(video)).thenReturn(expectedVideo);

        VideoFullResponseDto actualVideo = videoService.finById(id);

        Assertions.assertNotNull(actualVideo);
        Assertions.assertEquals(expectedVideo, actualVideo);
        verify(videoRepository, times(1)).findById(id);
        verify(videoMapper, times(1)).toFullDto(video);
    }

    @Test
    @DisplayName("Find video by invalid id should throw EntityNotFoundException")
    public void findById_withInvalidId_ShouldThrowEntityNotFoundException() {
        Long id = 100L;

        when(videoRepository.findById(id)).thenReturn(Optional.empty());

        String actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> videoService.finById(id)).getMessage();
        String expected = String.format(VIDEO_NOT_FOUND_EXCEPTION, id);

        Assertions.assertEquals(expected, actual);
        verify(videoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Find all videos should return Page of VideoShortResponseDto")
    public void findAll_shouldReturnPageOfVideoShortResponseDto() {
        Pageable pageable = Pageable.ofSize(10);
        Video video = VideoTestData.createDefaultVideo();
        VideoShortResponseDto expectedVideo = VideoTestData.createVideoShortResponseDto();
        Page<Video> videoPage = new PageImpl<>(Collections.singletonList(video));

        when(videoRepository.findAll(pageable)).thenReturn(videoPage);
        when(videoMapper.toShortDto(video)).thenReturn(expectedVideo);

        Page<VideoShortResponseDto> actualPage = videoService.findAll(pageable);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(1, actualPage.getTotalElements());
        Assertions.assertEquals(expectedVideo, actualPage.getContent().get(0));
        verify(videoRepository, times(1)).findAll(pageable);
        verify(videoMapper, times(1)).toShortDto(video);
    }

    @Test
    @DisplayName("Update video with valid data should return updated VideoFullResponseDto")
    public void updateVideo_withValidData_ShouldReturnUpdatedVideoFullResponseDto() {
        Long id = 1L;
        VideoRequestDto requestDto = VideoTestData.createVideoRequestDto();
        Video video = VideoTestData.createDefaultVideo();
        VideoFullResponseDto expectedVideo = VideoTestData.createVideoFullResponseDto();

        when(videoRepository.findById(id)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenReturn(video);
        when(videoMapper.toFullDto(video)).thenReturn(expectedVideo);

        VideoFullResponseDto actualVideo = videoService.update(id, requestDto);

        Assertions.assertNotNull(actualVideo);
        Assertions.assertEquals(expectedVideo, actualVideo);
        verify(videoRepository, times(1)).findById(id);
        verify(videoRepository, times(1)).save(video);
        verify(videoMapper, times(1)).toFullDto(video);
    }

    @Test
    @DisplayName("Update video with invalid id should throw EntityNotFoundException")
    public void updateVideo_withInvalidId_ShouldThrowEntityNotFoundException() {
        Long id = 100L;
        VideoRequestDto requestDto = VideoTestData.createVideoRequestDto();

        when(videoRepository.findById(id)).thenReturn(Optional.empty());

        String actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> videoService.update(id, requestDto)).getMessage();
        String expected = String.format(VIDEO_NOT_FOUND_EXCEPTION, id);

        Assertions.assertEquals(expected, actual);
        verify(videoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Delete video by valid id should successfully delete video")
    public void deleteVideo_withValidId_ShouldDeleteVideo() {
        Long id = 1L;
        Video video = VideoTestData.createDefaultVideo();

        when(videoRepository.findById(id)).thenReturn(Optional.of(video));

        videoService.delete(id);

        verify(videoRepository, times(1)).findById(id);
        verify(videoRepository, times(1)).delete(video);
    }

    @Test
    @DisplayName("Delete video by invalid id should throw EntityNotFoundException")
    public void deleteVideo_withInvalidId_ShouldThrowEntityNotFoundException() {
        Long id = 100L;

        when(videoRepository.findById(id)).thenReturn(Optional.empty());

        String actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> videoService.delete(id)).getMessage();
        String expected = String.format(VIDEO_NOT_FOUND_EXCEPTION, id);

        Assertions.assertEquals(expected, actual);
        verify(videoRepository, times(1)).findById(id);
    }
}
