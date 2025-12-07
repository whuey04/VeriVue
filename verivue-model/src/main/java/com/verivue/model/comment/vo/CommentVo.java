package com.verivue.model.comment.vo;
 
import com.verivue.model.comment.pojo.ApComment;
import lombok.Data;

@Data
public class CommentVo extends ApComment {
 
    /**
     * 0：Like
     * 1：Cancel Like
     */
    private Short operation;
}