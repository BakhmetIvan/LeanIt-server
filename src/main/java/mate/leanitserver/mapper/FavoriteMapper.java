package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.favorite.FavoriteRequestDto;
import mate.leanitserver.model.Favorite;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface FavoriteMapper {
    Favorite toModel(FavoriteRequestDto requestDto);
}
