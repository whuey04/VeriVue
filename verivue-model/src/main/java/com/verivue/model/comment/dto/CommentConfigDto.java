package com.verivue.model.comment.dto;

import lombok.Data;

@Data
public class CommentConfigDto {

    private Long articleId;

    /**
     * 0 Close comment  1 Open comment
     */
    private Short operation;
}
