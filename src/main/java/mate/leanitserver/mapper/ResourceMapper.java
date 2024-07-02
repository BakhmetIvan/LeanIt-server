package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.resource.ResourceFullResponseDto;
import mate.leanitserver.dto.resource.ResourceRequestDto;
import mate.leanitserver.dto.resource.ResourceShortResponseDto;
import mate.leanitserver.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ResourceMapper {
    ResourceShortResponseDto toShortDto(Resource resource);

    ResourceFullResponseDto toFullDto(Resource resource);

    Resource toModel(ResourceRequestDto requestDto);

    void updateResourceFromDto(@MappingTarget Resource resource, ResourceRequestDto requestDto);
}
