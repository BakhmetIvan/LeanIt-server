package mate.leanitserver.repository;

import java.util.Optional;
import mate.leanitserver.model.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleType, Long> {
    Optional<ArticleType> findByName(ArticleType.ArticleName name);
}
