package com.verivue.wemedia.controller;

import com.verivue.common.constants.WemediaConstants;
import com.verivue.model.wemedia.dto.NewsAuthDto;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.WmNewsDto;
import com.verivue.model.wemedia.dto.WmNewsPageReqDto;
import com.verivue.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    @Autowired
    private WmNewsService wmNewsService;

    /**
     * Query all article
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult findAllArticle(@RequestBody WmNewsPageReqDto dto){
        return wmNewsService.findAllArticle(dto);
    }

    /**
     * Submit or update article
     * @param wmNewsDto
     * @return
     */
    @PostMapping("/submit")
    public ResponseResult submitArticle(@RequestBody WmNewsDto wmNewsDto){
        return wmNewsService.submitArticle(wmNewsDto);
    }

    /**
     * Get Article Details
     * @param id
     * @return
     */
    @GetMapping("/one/{id}")
    public ResponseResult getArticleDetails(@PathVariable Long id){
        return wmNewsService.getArticleDetails(id);
    }

    /**
     * Delete Article
     * @param id
     * @return
     */
    @DeleteMapping("/del_news/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id) {
        return wmNewsService.deleteArticle(id);
    }

    /**
     * Publish or unpublish article
     * @param wmNewsDto
     * @return
     */
    @PostMapping("/down_or_up")
    public ResponseResult downOrUpNews(@RequestBody WmNewsDto wmNewsDto) {
        return wmNewsService.downOrUp(wmNewsDto);
    }

    //ADMIN

    /**
     * Get Article Review List in admin side
     * @param newsAuthDto
     * @return
     */
    @GetMapping("/list_vo")
    public ResponseResult getArticleReviewList(@RequestBody NewsAuthDto newsAuthDto){
        return wmNewsService.getArticleReviewList(newsAuthDto);
    }

    /**
     * Get Article Details
     * @param id
     * @return
     */
    @GetMapping("/one_vo/{id}")
    public ResponseResult getArticleReviewDetails(@PathVariable Long id){
        return wmNewsService.getArticleReviewDetails(id);
    }

    /**
     * Handle Article Review Rejected
     * @param dto
     * @return
     */
    @PostMapping("/auth_fail")
    public ResponseResult handleReviewRejected(@RequestBody NewsAuthDto dto) {
        return wmNewsService.updateReviewStatus(dto, WemediaConstants.WM_NEWS_AUTH_FAIL);
    }

    /**
     * Handle Article Review Approved
     * @param dto
     * @return
     */
    @PostMapping("/auth_pass")
    public ResponseResult handleReviewApproved(@RequestBody NewsAuthDto dto) {
        return wmNewsService.updateReviewStatus(dto, WemediaConstants.WM_NEWS_AUTH_PASS);
    }
}