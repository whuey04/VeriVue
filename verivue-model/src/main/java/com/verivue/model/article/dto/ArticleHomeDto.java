package com.verivue.model.article.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleHomeDto {

    //Maximum Time
    Date maxBehotTime;

    //Minimum Time
    Date minBehotTime;

    Integer size;

    String tag;
}