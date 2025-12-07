package com.verivue.wemedia.controller;

import com.verivue.model.article.dto.ArticleCommentDto;
import com.verivue.model.comment.dto.CommentConfigDto;
import com.verivue.model.comment.dto.CommentLikeDto;
import com.verivue.model.comment.dto.CommentManageDto;
import com.verivue.model.comment.dto.CommentReplySaveDto;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.wemedia.service.WmCommentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment/manage")
public class WmCommentManagerController {

    @Autowired
    private WmCommentManagerService commentManagerService;

    /**
     * Get Comment List in WeMedia platform
     * @param commentManageDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getCommentList(@RequestBody CommentManageDto commentManageDto) {
        return commentManagerService.getCommentList(commentManageDto);
    }

    @DeleteMapping("/del_comment/{commentId}")
    public ResponseResult deleteComment(@PathVariable String commentId) {
        return commentManagerService.deleteComment(commentId);
    }

    @DeleteMapping("/del_comment_reply/{commentReplyId}")
    public ResponseResult deleteCommentReply(@PathVariable("commentReplyId") String commentReplyId) {
        return commentManagerService.deleteCommentReply(commentReplyId);
    }

    @GetMapping("/find_news_comments")
    public PageResponseResult getArticleComments(@RequestBody ArticleCommentDto articleCommentDto) {
        return commentManagerService.getArticleComments(articleCommentDto);
    }

    @PostMapping("/update_comment_status")
    public ResponseResult updateCommentStatus(@RequestBody CommentConfigDto commentConfigDto) {
        return commentManagerService.updateCommentStatus(commentConfigDto);
    }

    @PostMapping("/comment_reply")
    public ResponseResult saveCommentReply(@RequestBody CommentReplySaveDto commentReplySaveDto) {
        return commentManagerService.saveCommentReply(commentReplySaveDto);
    }

    @PostMapping("/like")
    public ResponseResult likeComment(@RequestBody CommentLikeDto dto) {
        return commentManagerService.likeComment(dto);
    }
}
