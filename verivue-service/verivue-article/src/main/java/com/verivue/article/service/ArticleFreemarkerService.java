package com.verivue.article.service;

import com.verivue.model.article.pojo.ApArticle;

public interface ArticleFreemarkerService {

    /**
     * Generate Article file by Freemarker and upload it to minIO
     * @param apArticle
     * @param content
     */
    void generateArticleToMinIO(ApArticle apArticle, String content);
}