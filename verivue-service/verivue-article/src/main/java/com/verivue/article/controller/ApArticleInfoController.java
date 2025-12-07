package com.verivue.article.controller;

import com.verivue.article.service.ApArticleService;
import com.verivue.model.article.dto.ArticleInfoDto;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
public class ApArticleInfoController {

    @Autowired
    private ApArticleService apArticleService;

    /**
     * Display all the article behavior
     * @param dto
     * @return
     */
    @GetMapping("/load_article_behavior")
    public ResponseResult displayArticleBehavior(@RequestBody ArticleInfoDto dto) {
        return apArticleService.displayArticleBehavior(dto);
    }
}
