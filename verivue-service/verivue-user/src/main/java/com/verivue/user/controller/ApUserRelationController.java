package com.verivue.user.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.user.dto.UserRelationDto;
import com.verivue.user.service.ApUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ApUserRelationController {

    @Autowired
    private ApUserRelationService apUserRelationService;

    /**
     * Follow favorite creators
     * @param userRelationDto
     * @return
     */
    @PostMapping("/user_follow")
    public ResponseResult followCreator(@RequestBody UserRelationDto userRelationDto){
        return apUserRelationService.followCreator(userRelationDto);
    }
}
