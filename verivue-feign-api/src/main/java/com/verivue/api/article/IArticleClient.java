package com.verivue.api.article;

import com.verivue.api.article.fallback.IArticleClientFallback;
import com.verivue.model.article.dto.ArticleCommentDto;
import com.verivue.model.article.dto.ArticleDto;
import com.verivue.model.comment.dto.CommentConfigDto;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.StatisticsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "verivue-article", fallback = IArticleClientFallback.class)
public interface IArticleClient {

    /**
     * Save Article
     * @param articleDto
     * @return
     */
    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto articleDto);

    /**
     * Get Article Configuration by Article id
     * @param articleId
     * @return
     */
    @GetMapping("/api/v1/article/findArticleConfigByArticleId/{articleId}")
    ResponseResult getArticleConfigByArticleId(@PathVariable("articleId") Long articleId);

    /**
     * Get Article Comments
     * @param dto
     * @return
     */
    @PostMapping("/api/v1/article/findNewsComments")
    PageResponseResult getArticleComments(@RequestBody ArticleCommentDto dto);

    /**
     * Update Comment Service
     * @param dto
     * @return
     */
    @PostMapping("/api/v1/article/updateCommentStatus")
    ResponseResult updateCommentStatus(@RequestBody CommentConfigDto dto);

    /**
     * Image and text statistics.
     * @param wmUserId
     * @param beginDate
     * @param endDate
     * @return
     */
    @GetMapping("/api/v1/article/queryLikesAndCollections")
    ResponseResult getLikesAndCollections(@RequestParam("wmUserId") Long wmUserId, @RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate);

    /**
     * Paginated query for image and text statistics.
     * @param dto
     * @return
     */
    @PostMapping("/api/v1/article/newPage")
    PageResponseResult newPage(@RequestBody StatisticsDto dto);
}
