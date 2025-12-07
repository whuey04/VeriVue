package com.verivue.user.service;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.user.dto.UserRelationDto;

public interface ApUserRelationService {
    /**
     * Follow your favorite creators
     * @param userRelationDto
     * @return
     */
    ResponseResult followCreator(UserRelationDto userRelationDto);
}
