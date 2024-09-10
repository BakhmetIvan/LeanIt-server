package com.leanitserver.mapper;

import com.leanitserver.config.MapperConfig;
import com.leanitserver.dto.video.VideoFullResponseDto;
import com.leanitserver.dto.video.VideoRequestDto;
import com.leanitserver.dto.video.VideoShortResponseDto;
import com.leanitserver.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = AnkiMapper.class)
public interface VideoMapper {
    VideoShortResponseDto toShortDto(Video video);

    VideoFullResponseDto toFullDto(Video video);

    Video toModel(VideoRequestDto requestDto);

    void updateVideoFromDto(@MappingTarget Video video, VideoRequestDto requestDto);
}
