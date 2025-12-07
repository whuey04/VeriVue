package com.verivue.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.verivue.article.service.ApCollectionService;
import com.verivue.common.constants.BehaviorConstants;
import com.verivue.common.redis.CacheService;
import com.verivue.model.article.dto.CollectionBehaviorDto;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApCollectionServiceImpl implements ApCollectionService {

    @Autowired
    private CacheService cacheService;

    /**
     * Save article as favorite
     * @param dto
     * @return
     */
    @Override
    public ResponseResult collectionBehavior(CollectionBehaviorDto dto) {
       if (dto == null || dto.getEntryId() == null) {
           return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
       }

       ApUser user = AppThreadLocalUtil.getUser();
       if (user == null) {
           return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
       }

        String collectionJson = (String) cacheService.hGet(BehaviorConstants.COLLECTION_BEHAVIOR + user.getId(), dto.getEntryId().toString());
        if (StringUtils.isNotBlank(collectionJson) && dto.getOperation() == 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID," Article already collected");
        }

        if (dto.getOperation() == 0) {
            log.info("Save collected article key:{},{},{}",dto.getEntryId(),user.getId().toString(), JSON.toJSONString(dto));
            cacheService.hPut(BehaviorConstants.COLLECTION_BEHAVIOR + user.getId(), dto.getEntryId().toString(), JSON.toJSONString(dto));
        } else {
            log.info("Delete collected article key:{},{},{}",dto.getEntryId(),user.getId().toString(), JSON.toJSONString(dto));
            cacheService.hDelete(BehaviorConstants.COLLECTION_BEHAVIOR + user.getId(), dto.getEntryId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}
