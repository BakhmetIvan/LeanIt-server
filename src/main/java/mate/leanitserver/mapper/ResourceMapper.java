package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.resource.ResourceRequestDto;
import mate.leanitserver.dto.resource.ResourceResponseDto;
import mate.leanitserver.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ResourceMapper {
    ResourceResponseDto toDto(Resource resource);

    Resource toModel(ResourceRequestDto requestDto);

    void updateResourceFromDto(@MappingTarget Resource resource, ResourceRequestDto requestDto);
}
