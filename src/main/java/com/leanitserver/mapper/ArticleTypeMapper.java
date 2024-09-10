package com.leanitserver.mapper;

import com.leanitserver.config.MapperConfig;
import com.leanitserver.model.ArticleType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface ArticleTypeMapper {
    @Named("articleTypeToString")
    default String map(ArticleType type) {
        return type.getName().getValue();
    }
}
