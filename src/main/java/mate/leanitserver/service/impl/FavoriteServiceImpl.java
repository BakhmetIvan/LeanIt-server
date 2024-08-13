package mate.leanitserver.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.favorite.FavoriteRequestDto;
import mate.leanitserver.dto.favorite.FavoriteResponseDto;
import mate.leanitserver.exception.DuplicateException;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.mapper.FavoriteMapper;
import mate.leanitserver.model.ArticleType;
import mate.leanitserver.model.Favorite;
import mate.leanitserver.model.Searchable;
import mate.leanitserver.model.User;
import mate.leanitserver.repository.ArticleTypeRepository;
import mate.leanitserver.repository.FavoriteRepository;
import mate.leanitserver.repository.GrammarRepository;
import mate.leanitserver.repository.ResourceRepository;
import mate.leanitserver.repository.VideoRepository;
import mate.leanitserver.service.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private static final String ARTICLE_TYPE_NOT_FOUND_EXCEPTION =
            "Can't find article type by name: %s";
    private final GrammarRepository grammarRepository;
    private final VideoRepository videoRepository;
    private final ResourceRepository resourceRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;
    private final ArticleTypeRepository articleTypeRepository;

    @Transactional
    @Override
    public void addFavorite(User user, FavoriteRequestDto requestDto) {
        Favorite favorite = favoriteMapper.toModel(requestDto);
        ArticleType articleType = articleTypeRepository.findByName(
                ArticleType.ArticleName.valueOf(requestDto.getType().toUpperCase())).orElseThrow(
                                () -> new EntityNotFoundException(
                                        String.format(
                                                ARTICLE_TYPE_NOT_FOUND_EXCEPTION,
                                                requestDto.getType()))
        );
        favorite.setArticleType(articleType);
        favorite.setUser(user);
        if (favoriteRepository.findByArticleIdAndArticleTypeAndUser(
                favorite.getArticleId(),
                favorite.getArticleType(),
                user).isPresent()) {
            throw new DuplicateException("Favorites already added");
        }
        favoriteRepository.save(favorite);
    }

    @Transactional
    @Override
    public Page<FavoriteResponseDto> findAll(User user, Pageable pageable, String type) {
        ArticleType.ArticleName articleTypeName =
                ArticleType.ArticleName.valueOf(type.toUpperCase());
        ArticleType articleType = articleTypeRepository.findByName(articleTypeName).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format(ARTICLE_TYPE_NOT_FOUND_EXCEPTION, articleTypeName))
        );
        List<Favorite> favorites = favoriteRepository
                .findAllByUserAndArticleType(user, pageable, articleType).stream()
                .filter(favorite -> favorite.getArticleType().equals(articleType))
                .toList();
        List<Long> ids = favorites.stream()
                .map(Favorite::getArticleId)
                .toList();
        List<Searchable> results = new ArrayList<>();
        switch (articleTypeName) {
            case GRAMMAR -> {
                results.addAll(grammarRepository.findAllByIdIn(ids));
            }
            case VIDEO -> {
                results.addAll(videoRepository.findAllByIdIn(ids));
            }
            case RESOURCES -> {
                results.addAll(resourceRepository.findAllByIdIn(ids));
            }
            default -> {
                throw new RuntimeException("Incorrect article type");
            }
        }
        List<FavoriteResponseDto> favoriteResults = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            favoriteResults.add(favoriteMapper.toDto(results.get(i), favorites.get(i).getId()));
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), favoriteResults.size());
        List<FavoriteResponseDto> pageContent = favoriteResults.subList(start, end);
        return new PageImpl<>(pageContent, pageable, favoriteResults.size());
    }

    @Transactional
    @Override
    public void delete(User user, Long id) {
        Favorite favorite = favoriteRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Can't find favorite by id: %d", id))
        );
        favoriteRepository.delete(favorite);
    }
}
