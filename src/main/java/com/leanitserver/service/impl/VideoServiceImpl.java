package com.leanitserver.service.impl;

import com.leanitserver.dto.video.VideoFullResponseDto;
import com.leanitserver.dto.video.VideoRequestDto;
import com.leanitserver.dto.video.VideoShortResponseDto;
import com.leanitserver.exception.EntityNotFoundException;
import com.leanitserver.mapper.VideoMapper;
import com.leanitserver.model.ArticleType;
import com.leanitserver.model.Video;
import com.leanitserver.repository.ArticleTypeRepository;
import com.leanitserver.repository.VideoRepository;
import com.leanitserver.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private static final String NOT_FOUND_VIDEO_EXCEPTION = "Can't find video by id = %d";
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final ArticleTypeRepository articleTypeRepository;

    @Override
    public Page<VideoShortResponseDto> findAll(Pageable pageable) {
        return videoRepository.findAll(pageable)
                .map(videoMapper::toShortDto);
    }

    @Override
    public VideoFullResponseDto finById(Long id) {
        return videoMapper.toFullDto(videoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_VIDEO_EXCEPTION, id)))
        );
    }

    @Override
    public VideoFullResponseDto save(VideoRequestDto requestDto) {
        Video video = videoMapper.toModel(requestDto);
        video.setType(articleTypeRepository
                .findByName(ArticleType.ArticleName.VIDEO)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find article type"))
        );
        return videoMapper.toFullDto(videoRepository.save(video));
    }

    @Transactional
    @Override
    public VideoFullResponseDto update(Long id, VideoRequestDto requestDto) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_VIDEO_EXCEPTION, id))
        );
        videoMapper.updateVideoFromDto(video, requestDto);
        return videoMapper.toFullDto(videoRepository.save(video));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_VIDEO_EXCEPTION, id))
        );
        videoRepository.delete(video);
    }
}
