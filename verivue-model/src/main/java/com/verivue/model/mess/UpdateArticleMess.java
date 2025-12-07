package com.verivue.model.mess;

import lombok.Data;

@Data
public class UpdateArticleMess {

    private UpdateArticleType type;

    private Long articleId;

    private Integer add;

    public enum UpdateArticleType{
        COLLECTION,COMMENT,LIKES,VIEWS;
    }
}