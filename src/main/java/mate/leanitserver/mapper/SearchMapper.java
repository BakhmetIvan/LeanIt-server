package mate.leanitserver.mapper;

import mate.leanitserver.config.MapperConfig;
import mate.leanitserver.dto.search.SearchResponseDto;
import mate.leanitserver.model.Searchable;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface SearchMapper {
    SearchResponseDto toDto(Searchable searchable);
}
