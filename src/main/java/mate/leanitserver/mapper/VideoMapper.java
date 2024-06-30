package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.video.VideoFullResponseDto;
import mate.leanitserver.dto.video.VideoRelatedDto;
import mate.leanitserver.dto.video.VideoShortResponseDto;
import mate.leanitserver.model.Video;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface VideoMapper {
    VideoShortResponseDto toShortDto(Video video);

    VideoFullResponseDto toFullDto(Video video);

    VideoRelatedDto toRelatedDto(Video video);
}
