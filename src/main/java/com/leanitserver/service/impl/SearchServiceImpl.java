package com.leanitserver.service.impl;

import com.leanitserver.dto.search.SearchResponseDto;
import com.leanitserver.mapper.SearchMapper;
import com.leanitserver.model.Searchable;
import com.leanitserver.repository.GrammarRepository;
import com.leanitserver.repository.ResourceRepository;
import com.leanitserver.repository.VideoRepository;
import com.leanitserver.service.SearchService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final GrammarRepository grammarRepository;
    private final VideoRepository videoRepository;
    private final ResourceRepository resourceRepository;
    private final SearchMapper searchMapper;

    @Transactional
    @Override
    public List<SearchResponseDto> findAllByTitle(String title, Pageable pageable) {
        List<Searchable> results = new ArrayList<>();
        results.addAll(grammarRepository.findAllByTitle(title));
        results.addAll(videoRepository.findAllByTitle(title));
        results.addAll(resourceRepository.findAllByTitle(title));
        return results.stream()
                .sorted(Comparator.comparing(Searchable::getTitle))
                .limit(pageable.getPageSize())
                .map(searchMapper::toDto)
                .toList();
    }
}
