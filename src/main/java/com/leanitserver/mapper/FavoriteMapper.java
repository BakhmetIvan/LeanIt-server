package com.leanitserver.mapper;

import com.leanitserver.config.MapperConfig;
import com.leanitserver.dto.favorite.FavoriteRequestDto;
import com.leanitserver.dto.favorite.FavoriteResponseDto;
import com.leanitserver.model.Favorite;
import com.leanitserver.model.Searchable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface FavoriteMapper {
    Favorite toModel(FavoriteRequestDto requestDto);

    @Mapping(target = "favoriteId", source = "favoriteId")
    FavoriteResponseDto toDto(Searchable searchable, Long favoriteId);
}
