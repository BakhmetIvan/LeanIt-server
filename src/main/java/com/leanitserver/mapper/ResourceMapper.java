package com.leanitserver.mapper;

import com.leanitserver.config.MapperConfig;
import com.leanitserver.dto.resource.ResourceRequestDto;
import com.leanitserver.dto.resource.ResourceResponseDto;
import com.leanitserver.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ResourceMapper {
    ResourceResponseDto toDto(Resource resource);

    Resource toModel(ResourceRequestDto requestDto);

    void updateResourceFromDto(@MappingTarget Resource resource, ResourceRequestDto requestDto);
}
