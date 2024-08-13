package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.favorite.FavoriteRequestDto;
import mate.leanitserver.dto.favorite.FavoriteResponseDto;
import mate.leanitserver.model.Favorite;
import mate.leanitserver.model.Searchable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface FavoriteMapper {
    Favorite toModel(FavoriteRequestDto requestDto);

    @Mapping(target = "favoriteId", source = "favoriteId")
    FavoriteResponseDto toDto(Searchable searchable, Long favoriteId);
}
