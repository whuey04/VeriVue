package com.verivue.behavior.service;

import com.verivue.model.behavior.dto.LikesBehaviorDto;
import com.verivue.model.behavior.dto.ReadBehaviorDto;
import com.verivue.model.behavior.dto.UnLikesBehaviorDto;
import com.verivue.model.common.dto.ResponseResult;

public interface BehaviorService {

    /**
     * Like/Unlike the comments, articles and others
     * @param likesBehaviorDto
     * @return
     */
    ResponseResult likeBehavior(LikesBehaviorDto likesBehaviorDto);

    /**
     *  Article view statistics
     * @param readBehaviorDto
     * @return
     */
    ResponseResult readBehavior(ReadBehaviorDto readBehaviorDto);

    /**
     * Dislike articles
     * @param unLikesBehaviorDto
     * @return
     */
    ResponseResult dislikeBehavior(UnLikesBehaviorDto unLikesBehaviorDto);
}
