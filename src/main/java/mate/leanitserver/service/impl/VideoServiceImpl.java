package mate.leanitserver.service.impl;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.video.VideoFullResponseDto;
import mate.leanitserver.dto.video.VideoRequestDto;
import mate.leanitserver.dto.video.VideoShortResponseDto;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.mapper.VideoMapper;
import mate.leanitserver.model.Video;
import mate.leanitserver.repository.VideoRepository;
import mate.leanitserver.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private static final String NOT_FOUND_VIDEO_EXCEPTION = "Can't find video by id = %d";
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

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
        return videoMapper.toFullDto(videoRepository.save(video));
    }

    @Override
    public VideoFullResponseDto update(Long id, VideoRequestDto requestDto) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_VIDEO_EXCEPTION, id))
        );
        videoMapper.updateVideoFromDto(video, requestDto);
        return videoMapper.toFullDto(videoRepository.save(video));
    }

    @Override
    public void delete(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_VIDEO_EXCEPTION, id))
        );
        videoRepository.delete(video);
    }
}
