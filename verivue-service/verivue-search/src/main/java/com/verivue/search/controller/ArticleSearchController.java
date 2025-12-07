package com.verivue.search.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.search.dto.UserSearchDto;
import com.verivue.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/article/search")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * Search Article
     * @param dto
     * @return
     * @throws IOException
     */
    @PostMapping
    public ResponseResult searchArticle(@RequestBody UserSearchDto dto) throws IOException {
        return articleSearchService.searchArticle(dto);
    }
}
