package com.verivue.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.article.pojo.ApArticleConfig;
import com.verivue.model.comment.dto.CommentConfigDto;
import com.verivue.model.common.dto.ResponseResult;

import java.util.Map;

public interface ApArticleConfigService extends IService<ApArticleConfig> {

    /**
     * Kafka listener for article publish/unpublish events.
     * @param map
     */
    void updateByMap(Map map);

    /**
     * Update Comment Status
     * @param dto
     * @return
     */
    ResponseResult updateCommentStatus(CommentConfigDto dto);
}
