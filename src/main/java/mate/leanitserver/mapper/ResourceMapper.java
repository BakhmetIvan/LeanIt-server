package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.resource.ResourceFullResponseDto;
import mate.leanitserver.dto.resource.ResourceShortResponseDto;
import mate.leanitserver.model.Resource;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ResourceMapper {
    ResourceShortResponseDto toShortDto(Resource resource);

    ResourceFullResponseDto toFullDto(Resource resource);
}
