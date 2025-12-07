package com.verivue.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.verivue.behavior.service.BehaviorService;
import com.verivue.common.constants.BehaviorConstants;
import com.verivue.common.constants.HotArticleConstants;
import com.verivue.common.redis.CacheService;
import com.verivue.model.behavior.dto.LikesBehaviorDto;
import com.verivue.model.behavior.dto.ReadBehaviorDto;
import com.verivue.model.behavior.dto.UnLikesBehaviorDto;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.mess.UpdateArticleMess;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class BehaviorServiceImpl implements BehaviorService {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Like/Unlike the comments, articles and others
     *
     * @param likesBehaviorDto
     * @return
     */
    @Override
    public ResponseResult likeBehavior(LikesBehaviorDto likesBehaviorDto) {
        if (likesBehaviorDto == null || likesBehaviorDto.getArticleId() == null || checkParam(likesBehaviorDto)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(likesBehaviorDto.getArticleId());
        mess.setType(UpdateArticleMess.UpdateArticleType.LIKES);

        if (likesBehaviorDto.getOperation() == 0){
            Object object = cacheService.hGet(
                    BehaviorConstants.LIKE_BEHAVIOR + likesBehaviorDto.getArticleId().toString(),
                    user.getId().toString());
            if (object != null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Liked");
            }
            log.info("Save current key:{}, {}, {}",likesBehaviorDto.getArticleId(), user.getId(), likesBehaviorDto);
            cacheService.hPut(
                    BehaviorConstants.LIKE_BEHAVIOR + likesBehaviorDto.getArticleId().toString(),
                    user.getId().toString(),
                    JSON.toJSONString(likesBehaviorDto));
            mess.setAdd(1);
        }else {
            log.info("Delete current key:{}, {}", likesBehaviorDto.getArticleId(), user.getId());
            cacheService.hDelete(
                    BehaviorConstants.LIKE_BEHAVIOR + likesBehaviorDto.getArticleId().toString(),
                    user.getId().toString());
            mess.setAdd(-1);
        }

        // Send message for data aggregation
        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(mess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Article view statistics
     *
     * @param readBehaviorDto
     * @return
     */
    @Override
    public ResponseResult readBehavior(ReadBehaviorDto readBehaviorDto) {
        if (readBehaviorDto == null || readBehaviorDto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        // Update reading time
        String readBehaviorDataInJson = (String) cacheService.hGet(
                BehaviorConstants.READ_BEHAVIOR + readBehaviorDto.getArticleId().toString(),
                user.getId().toString());
        if(StringUtils.isNotBlank(readBehaviorDataInJson)){
            ReadBehaviorDto dto = JSON.parseObject(readBehaviorDataInJson, ReadBehaviorDto.class);
            readBehaviorDto.setCount((short) (dto.getCount() + readBehaviorDto.getCount()));
        }

        //Save current key
        log.info("Save Read key:{}, {}, {}", readBehaviorDto.getArticleId(), user.getId(), readBehaviorDto);
        cacheService.hPut(BehaviorConstants.READ_BEHAVIOR + readBehaviorDto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(readBehaviorDto));

        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(readBehaviorDto.getArticleId());
        mess.setType(UpdateArticleMess.UpdateArticleType.VIEWS);
        mess.setAdd(1);

        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(mess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    /**
     * Dislike articles
     *
     * @param unLikesBehaviorDto
     * @return
     */
    @Override
    public ResponseResult dislikeBehavior(UnLikesBehaviorDto unLikesBehaviorDto) {
        if (unLikesBehaviorDto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        if (unLikesBehaviorDto.getType() == 0) {
            log.info("Save Unlikes key:{}, {}, {}", unLikesBehaviorDto.getArticleId(), user.getId(), unLikesBehaviorDto);
            cacheService.hPut(BehaviorConstants.UN_LIKE_BEHAVIOR + unLikesBehaviorDto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(unLikesBehaviorDto));
        } else {
            log.info("Delete Unlikes key:{}, {}, {}", unLikesBehaviorDto.getArticleId(), user.getId(), unLikesBehaviorDto);
            cacheService.hDelete(BehaviorConstants.UN_LIKE_BEHAVIOR + unLikesBehaviorDto.getArticleId().toString(), user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }


    private boolean checkParam(LikesBehaviorDto dto) {
        if (dto.getType() > 2
                || dto.getType() < 0
                || dto.getOperation() > 1
                || dto.getOperation() < 0) {
            return true;
        }
        return false;
    }
}
