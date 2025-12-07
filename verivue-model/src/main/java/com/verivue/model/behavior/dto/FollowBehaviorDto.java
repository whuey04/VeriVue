package com.verivue.model.behavior.dto;

import lombok.Data;

@Data
public class FollowBehaviorDto {

    Long articleId;

    Long followId;

    Long userId;
}