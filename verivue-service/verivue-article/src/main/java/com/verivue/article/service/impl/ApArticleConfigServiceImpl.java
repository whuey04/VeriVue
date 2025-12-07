package com.verivue.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.article.mapper.ApArticleConfigMapper;
import com.verivue.article.service.ApArticleConfigService;
import com.verivue.model.article.pojo.ApArticleConfig;
import com.verivue.model.comment.dto.CommentConfigDto;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
@Transactional
public class ApArticleConfigServiceImpl extends ServiceImpl<ApArticleConfigMapper, ApArticleConfig> implements ApArticleConfigService {
    /**
     * Kafka listener for article publish/unpublish events.
     *
     * @param map
     */
    @Override
    public void updateByMap(Map map) {
        //0 Publish 1 UnPublish
        Object enable = map.get("enable");
        boolean isUnpublish= true;
        if(enable.equals(1)){
            isUnpublish = false;
        }

        update(Wrappers.<ApArticleConfig>lambdaUpdate()
                .eq(ApArticleConfig::getArticleId,map.get("articleId"))
                .set(ApArticleConfig::getIsDown,isUnpublish));
    }

    /**
     * Update Comment Status
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateCommentStatus(CommentConfigDto dto) {
        update(Wrappers.<ApArticleConfig>lambdaUpdate()
                .eq(ApArticleConfig::getArticleId, dto.getArticleId())
                .set(ApArticleConfig::getIsComment, dto.getOperation()));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
