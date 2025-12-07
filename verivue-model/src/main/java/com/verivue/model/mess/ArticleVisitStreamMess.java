package com.verivue.model.mess;

import lombok.Data;

@Data
public class ArticleVisitStreamMess {

    private Long articleId;

    private int view;

    private int collect;

    private int comment;

    private int like;
}