package mate.leanitserver.service.impl;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.video.VideoFullResponseDto;
import mate.leanitserver.dto.video.VideoShortResponseDto;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.mapper.VideoMapper;
import mate.leanitserver.repository.VideoRepository;
import mate.leanitserver.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
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
                        "Can't find video by id = %d", id)))
        );
    }
}
