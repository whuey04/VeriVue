package com.verivue.article.controller;

import com.verivue.article.service.ApArticleService;
import com.verivue.common.constants.ArticleConstants;
import com.verivue.model.article.dto.ArticleHomeDto;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    @Autowired
    private ApArticleService apArticleService;

    @GetMapping("/load")
    public ResponseResult loadArticle(@RequestBody ArticleHomeDto articleHomeDto) {
//        return apArticleService.loadArticleWithType(articleHomeDto, ArticleConstants.LOADTYPE_LOAD_MORE);
        return apArticleService.loadArticleWithType2(articleHomeDto, ArticleConstants.LOADTYPE_LOAD_MORE, true);

    }

    @GetMapping("/loadmore")
    public ResponseResult loadMoreArticle(@RequestBody ArticleHomeDto articleHomeDto) {
        return apArticleService.loadArticleWithType(articleHomeDto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    @GetMapping("/loadnew")
    public ResponseResult loadNewArticle(@RequestBody ArticleHomeDto articleHomeDto) {
        return apArticleService.loadArticleWithType(articleHomeDto, ArticleConstants.LOADTYPE_LOAD_NEW);
    }
}
