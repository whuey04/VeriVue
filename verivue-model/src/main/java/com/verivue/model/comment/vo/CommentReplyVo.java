package com.verivue.model.comment.vo;
 
import com.verivue.model.comment.pojo.ApCommentReply;
import lombok.Data;
 
@Data
public class CommentReplyVo extends ApCommentReply {

    /**
     * 0：Like
     * 1：Cancel like
     */
    private Short operation;
}