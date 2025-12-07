package com.verivue.model.comment.dto;

import lombok.Data;

@Data
public class CommentReplyLikeDto {

    private String commentReplyId;

    /**
     * 0 Like 1 Cancel Like
     */
    private Short operation;
}
