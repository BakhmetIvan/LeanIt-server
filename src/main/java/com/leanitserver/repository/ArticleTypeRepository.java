package com.leanitserver.repository;

import com.leanitserver.model.ArticleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleType, Long> {
    Optional<ArticleType> findByName(ArticleType.ArticleName name);
}
