package com.leanitserver.testdata;

import com.leanitserver.model.ArticleType;

public class ArticleTestData {
    public static ArticleType createGrammarArticleType() {
        ArticleType articleType = new ArticleType();
        articleType.setId(1L);
        articleType.setName(ArticleType.ArticleName.GRAMMAR);
        return articleType;
    }

    public static ArticleType createVideoArticleType() {
        ArticleType articleType = new ArticleType();
        articleType.setId(2L);
        articleType.setName(ArticleType.ArticleName.VIDEO);
        return articleType;
    }

    public static ArticleType createResourcesArticleType() {
        ArticleType articleType = new ArticleType();
        articleType.setId(3L);
        articleType.setName(ArticleType.ArticleName.RESOURCES);
        return articleType;
    }
}
