package com.verivue.model.article.dto;

import com.verivue.model.article.pojo.ApArticle;
import lombok.Data;

@Data
public class ArticleDto  extends ApArticle {

    private String content;
}