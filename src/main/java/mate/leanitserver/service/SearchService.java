package mate.leanitserver.service;

import java.util.List;
import mate.leanitserver.dto.search.SearchResponseDto;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    List<SearchResponseDto> findAllByTitle(String title, Pageable pageable);
}
