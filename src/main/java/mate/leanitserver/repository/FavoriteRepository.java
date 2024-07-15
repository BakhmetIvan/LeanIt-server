package mate.leanitserver.repository;

import java.util.List;
import mate.leanitserver.model.ArticleType;
import mate.leanitserver.model.Favorite;
import mate.leanitserver.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUserAndArticleType(User user, Pageable pageable,
                                               ArticleType articleType);

    Favorite findByIdAndUser(Long id, User user);
}
