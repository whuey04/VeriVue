package com.verivue.model.comment.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    private Long articleId;

    private Date minDate;

    private Short index;
}
