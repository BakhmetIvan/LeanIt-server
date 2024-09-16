package com.leanitserver.mapper;

import com.leanitserver.config.MapperConfig;
import com.leanitserver.dto.search.SearchResponseDto;
import com.leanitserver.model.Searchable;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface SearchMapper {
    SearchResponseDto toDto(Searchable searchable);
}
