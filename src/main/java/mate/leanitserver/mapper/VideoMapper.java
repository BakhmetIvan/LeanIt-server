package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.video.VideoFullResponseDto;
import mate.leanitserver.dto.video.VideoRequestDto;
import mate.leanitserver.dto.video.VideoShortResponseDto;
import mate.leanitserver.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = AnkiMapper.class)
public interface VideoMapper {
    VideoShortResponseDto toShortDto(Video video);

    VideoFullResponseDto toFullDto(Video video);

    Video toModel(VideoRequestDto requestDto);

    void updateVideoFromDto(@MappingTarget Video video, VideoRequestDto requestDto);
}
