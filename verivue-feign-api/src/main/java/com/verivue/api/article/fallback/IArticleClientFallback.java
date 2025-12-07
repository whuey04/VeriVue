package com.verivue.api.article.fallback;

import com.verivue.api.article.IArticleClient;
import com.verivue.model.article.dto.ArticleCommentDto;
import com.verivue.model.article.dto.ArticleDto;
import com.verivue.model.comment.dto.CommentConfigDto;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.wemedia.dto.StatisticsDto;
import org.springframework.stereotype.Component;

/**
 * Feign failure configuration
 */
@Component
public class IArticleClientFallback implements IArticleClient {
    @Override
    public ResponseResult saveArticle(ArticleDto articleDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"Failed to retrieve data ");
    }

    @Override
    public ResponseResult getArticleConfigByArticleId(Long articleId) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"Failed to retrieve data ");
    }

    /**
     * Get Article Comments
     *
     * @param dto
     * @return
     */
    @Override
    public PageResponseResult getArticleComments(ArticleCommentDto dto) {
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),0);
        responseResult.setCode(501);
        responseResult.setMessage("Failed to retrieve data ");
        return responseResult;
    }

    /**
     * Update Comment Service
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateCommentStatus(CommentConfigDto dto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "Failed to update comment status ");
    }

    /**
     * 图文统计
     *
     * @param wmUserId
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public ResponseResult getLikesAndCollections(Long wmUserId, String beginDate, String endDate) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "Failed to retrieve data ");
    }

    /**
     * 图文统计 分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public PageResponseResult newPage(StatisticsDto dto) {
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),0);
        responseResult.setCode(501);
        responseResult.setMessage("Failed to retrieve data ");
        return responseResult;
    }
}
