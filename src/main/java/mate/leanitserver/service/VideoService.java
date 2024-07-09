package mate.leanitserver.service;

import mate.leanitserver.dto.video.VideoFullResponseDto;
import mate.leanitserver.dto.video.VideoRequestDto;
import mate.leanitserver.dto.video.VideoShortResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoService {
    Page<VideoShortResponseDto> findAll(Pageable pageable);

    VideoFullResponseDto finById(Long id);

    VideoFullResponseDto save(VideoRequestDto requestDto);

    VideoFullResponseDto update(Long id, VideoRequestDto requestDto);

    void delete(Long id);
}
