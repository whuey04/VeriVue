package com.verivue.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.wemedia.dto.NewsAuthDto;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.WmNewsDto;
import com.verivue.model.wemedia.dto.WmNewsPageReqDto;
import com.verivue.model.wemedia.pojo.WmNews;

public interface WmNewsService extends IService<WmNews> {
    /**
     * Query all article
     * @param dto
     * @return
     */
    ResponseResult findAllArticle(WmNewsPageReqDto dto);

    /**
     * Save/Update article or draft
     * @param wmNewsDto
     * @return
     */
    ResponseResult submitArticle(WmNewsDto wmNewsDto);

    /**
     * Get Article Details
     * @param id
     * @return
     */
    ResponseResult getArticleDetails(Long id);

    /**
     * Delete Article
     * @param id
     * @return
     */
    ResponseResult deleteArticle(Long id);

    /**
     * Publish or unpublish article
     * @param wmNewsDto
     * @return
     */
    ResponseResult downOrUp(WmNewsDto wmNewsDto);

    /**
     * Get Article Review List in admin side
     * @param newsAuthDto
     * @return
     */
    ResponseResult getArticleReviewList(NewsAuthDto newsAuthDto);

    /**
     * Get Article Details in admin side
     * @param id
     * @return
     */
    ResponseResult getArticleReviewDetails(Long id);

    /**
     * Update the article review status
     * @param dto
     * @param status
     * @return
     */
    ResponseResult updateReviewStatus(NewsAuthDto dto, Short status);
}
