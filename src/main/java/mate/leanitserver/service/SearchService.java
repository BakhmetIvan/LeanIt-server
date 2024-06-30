package mate.leanitserver.service;

import java.util.List;
import mate.leanitserver.dto.search.SearchResponseDto;

public interface SearchService {
    List<SearchResponseDto> findAllByTitle(String title);
}
