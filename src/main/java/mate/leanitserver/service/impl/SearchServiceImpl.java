package mate.leanitserver.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.search.SearchResponseDto;
import mate.leanitserver.mapper.SearchMapper;
import mate.leanitserver.model.Searchable;
import mate.leanitserver.repository.GrammarRepository;
import mate.leanitserver.repository.ResourceRepository;
import mate.leanitserver.repository.VideoRepository;
import mate.leanitserver.service.SearchService;
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
    public List<SearchResponseDto> findAllByTitle(String title) {
        List<Searchable> results = new ArrayList<>();
        results.addAll(grammarRepository.findAllByTitle(title));
        results.addAll(videoRepository.findAllByTitle(title));
        results.addAll(resourceRepository.findAllByTitle(title));
        return results.stream()
                .sorted(Comparator.comparing(Searchable::getTitle))
                .map(searchMapper::toDto)
                .toList();
    }
}
