package com.verivue.model.article.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleCommentVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private Integer comments;

    private Boolean isComment;

    private Date publishTime;

}