package com.leanitserver.service;

import com.leanitserver.dto.search.SearchResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    List<SearchResponseDto> findAllByTitle(String title, Pageable pageable);
}
