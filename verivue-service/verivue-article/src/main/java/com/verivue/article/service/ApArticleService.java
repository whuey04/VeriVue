package com.verivue.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.article.dto.ArticleCommentDto;
import com.verivue.model.article.dto.ArticleDto;
import com.verivue.model.article.dto.ArticleHomeDto;
import com.verivue.model.article.dto.ArticleInfoDto;
import com.verivue.model.mess.ArticleVisitStreamMess;
import com.verivue.model.article.pojo.ApArticle;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.StatisticsDto;

import java.util.Date;

public interface ApArticleService extends IService<ApArticle> {
    /**
     * Load Article with different type in user app
     * @param articleHomeDto
     * @param loadType
     * @return
     */
    ResponseResult loadArticleWithType(ArticleHomeDto articleHomeDto, Short loadType);

    /**
     * Save article
     * @param articleDto
     * @return
     */
    ResponseResult saveArticle(ArticleDto articleDto);

    /**
     * Display all the article behavior in app side
     * @param dto
     * @return
     */
    ResponseResult displayArticleBehavior(ArticleInfoDto dto);

    /**
     * Load Article with different type in user app
     * @param articleHomeDto
     * @param loadType
     * @param firstPage
     * @return
     */
    ResponseResult loadArticleWithType2(ArticleHomeDto articleHomeDto, Short loadType, boolean firstPage);

    /**
     * Update article score and cache hot articles
     * @param mess
     */
    void updateArticleScore(ArticleVisitStreamMess mess);

    /**
     * Get Article Comments
     * @param dto
     * @return
     */
    PageResponseResult getArticleComments(ArticleCommentDto dto);

    ResponseResult getLikesAndCollections(Long wmUserId, Date beginDate, Date endDate);

    PageResponseResult newPage(StatisticsDto dto);
}
