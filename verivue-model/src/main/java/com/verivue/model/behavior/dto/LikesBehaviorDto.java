package com.verivue.model.behavior.dto;

import lombok.Data;

@Data
public class LikesBehaviorDto {

    Long articleId;

    /**
     * Types of content that can be liked:
     * 0 - Article
     * 1 - Post
     * 2 - Comment
     */
    Short type;

    /**
     * Operation
     * 0 Like 1 Unlike
     */
    Short operation;
}