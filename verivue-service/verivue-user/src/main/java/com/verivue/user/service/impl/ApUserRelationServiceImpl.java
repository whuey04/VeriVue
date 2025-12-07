package com.verivue.user.service.impl;

import com.verivue.common.constants.BehaviorConstants;
import com.verivue.common.redis.CacheService;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.user.dto.UserRelationDto;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.user.service.ApUserRelationService;
import com.verivue.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApUserRelationServiceImpl implements ApUserRelationService {

    @Autowired
    private CacheService cacheService;

    /**
     * Follow your favorite creators
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult followCreator(UserRelationDto dto) {
        if (dto.getOperation() == null || dto.getOperation() < 0 || dto.getOperation() > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Long userId = user.getId();

        Long authorId = dto.getAuthorId();

        if (dto.getOperation() == 0){
            //Record the author id to my list of follow
            cacheService.zAdd(
                    BehaviorConstants.APUSER_FOLLOW_RELATION + userId,
                    authorId.toString(),
                    System.currentTimeMillis());

            //Record my id to the creator's fans list
            cacheService.zAdd(
                    BehaviorConstants.APUSER_FANS_RELATION + authorId,
                    userId.toString(),
                    System.currentTimeMillis());
        }else {
            //UnFollow
            cacheService.zRemove(
                    BehaviorConstants.APUSER_FOLLOW_RELATION + userId,
                    authorId.toString());
            cacheService.zRemove(
                    BehaviorConstants.APUSER_FANS_RELATION + authorId,
                    userId.toString());
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
