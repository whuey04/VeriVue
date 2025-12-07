package com.verivue.model.comment.dto;

import lombok.Data;

@Data
public class CommentLikeDto {

    private String commentId;

    /**
     * 0 Like comment 1 Cancel comment's like
     */
    private Short operation;
}
