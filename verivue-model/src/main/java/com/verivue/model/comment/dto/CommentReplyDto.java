package com.verivue.model.comment.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentReplyDto {

    private String commentId;

    private Integer size;

    private Date minDate;
}
