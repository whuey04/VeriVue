package com.verivue.article.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.verivue.api.article.IArticleClient;
import com.verivue.article.service.ApArticleConfigService;
import com.verivue.article.service.ApArticleService;
import com.verivue.model.article.dto.ArticleCommentDto;
import com.verivue.model.article.dto.ArticleDto;
import com.verivue.model.article.pojo.ApArticleConfig;
import com.verivue.model.comment.dto.CommentConfigDto;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.wemedia.dto.StatisticsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ArticleClient implements IArticleClient {

    @Autowired
    private ApArticleService apArticleService;
    @Autowired
    private ApArticleConfigService apArticleConfigService;

    @Override
    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto articleDto) {
        return apArticleService.saveArticle(articleDto);
    }

    @Override
    @GetMapping("/api/v1/article/findArticleConfigByArticleId/{articleId}")
    public ResponseResult getArticleConfigByArticleId(@PathVariable("articleId") Long articleId) {
        ApArticleConfig articleConfig = apArticleConfigService.getOne(Wrappers.<ApArticleConfig>lambdaQuery()
                .eq(ApArticleConfig::getArticleId, articleId));
        return ResponseResult.okResult(articleConfig);
    }

    /**
     * Get Article Comments
     *
     * @param dto
     * @return
     */
    @Override
    @PostMapping("/api/v1/article/findNewsComments")
    public PageResponseResult getArticleComments(@RequestBody ArticleCommentDto dto) {
        return apArticleService.getArticleComments(dto);
    }

    /**
     * Update Comment Service
     *
     * @param dto
     * @return
     */
    @Override
    @PostMapping("/api/v1/article/updateCommentStatus")
    public ResponseResult updateCommentStatus(@RequestBody CommentConfigDto dto) {
        return apArticleConfigService.updateCommentStatus(dto);
    }

    /**
     * 图文统计
     *
     * @param wmUserId
     * @param beginDate
     * @param endDate
     * @return
     */
    @GetMapping("/api/v1/article/queryLikesAndCollections")
    @Override
    public ResponseResult getLikesAndCollections(
            @RequestParam("wmUserId") Long wmUserId, @RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDateTime = null;
        Date endDateTime = null;

        try {
            beginDateTime = sdf.parse(beginDate);
            endDateTime = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"日期格式不正确");
        }


        return apArticleService.getLikesAndCollections(wmUserId, beginDateTime, endDateTime);
    }

    /**
     * Image and Text statistics
     *
     * @param dto
     * @return
     */
    @PostMapping("/api/v1/article/newPage")
    @Override
    public PageResponseResult newPage(@RequestBody StatisticsDto dto) {
        return apArticleService.newPage(dto);
    }
}
