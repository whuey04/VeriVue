package com.verivue.behavior.controller;

import com.verivue.behavior.service.BehaviorService;
import com.verivue.model.behavior.dto.LikesBehaviorDto;
import com.verivue.model.behavior.dto.ReadBehaviorDto;
import com.verivue.model.behavior.dto.UnLikesBehaviorDto;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BehaviorController {

    @Autowired
    private BehaviorService behaviorService;

    /**
     * Like/Unlike the comments, articles and others
     * @param likesBehaviorDto
     * @return
     */
    @PostMapping("/likes_behavior")
    public ResponseResult likeBehavior(@RequestBody LikesBehaviorDto likesBehaviorDto) {
        return behaviorService.likeBehavior(likesBehaviorDto);
    }

    /**
     *  Article view statistics
     * @param readBehaviorDto
     * @return
     */
    @PostMapping("/read_behavior")
    public ResponseResult readBehavior(@RequestBody ReadBehaviorDto readBehaviorDto) {
        return behaviorService.readBehavior(readBehaviorDto);
    }

    /**
     * Dislike articles
     * @param unLikesBehaviorDto
     * @return
     */
    @PostMapping("/un_likes_behavior")
    public ResponseResult dislikeBehavior(@RequestBody UnLikesBehaviorDto unLikesBehaviorDto) {
        return behaviorService.dislikeBehavior(unLikesBehaviorDto);
    }
}
