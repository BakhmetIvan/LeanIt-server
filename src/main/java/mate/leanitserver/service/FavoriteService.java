package mate.leanitserver.service;

import mate.leanitserver.dto.favorite.FavoriteRequestDto;
import mate.leanitserver.dto.search.SearchResponseDto;
import mate.leanitserver.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {
    void addFavorite(User user, FavoriteRequestDto requestDto);

    Page<SearchResponseDto> findAll(User user, Pageable pageable, String type);

    void delete(User user, Long id);
}
