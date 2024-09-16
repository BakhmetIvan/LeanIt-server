package com.leanitserver.repository;

import com.leanitserver.model.ArticleType;
import com.leanitserver.model.Favorite;
import com.leanitserver.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUserAndArticleType(User user, Pageable pageable,
                                               ArticleType articleType);

    Optional<Favorite> findByIdAndUser(Long id, User user);

    Optional<Favorite> findByArticleIdAndArticleTypeAndUser(Long articleId,
                                                            ArticleType articleType,
                                                            User user);
}
