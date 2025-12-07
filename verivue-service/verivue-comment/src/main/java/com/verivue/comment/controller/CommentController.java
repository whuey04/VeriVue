package com.verivue.comment.controller;

import com.verivue.comment.service.CommentService;
import com.verivue.model.comment.dto.CommentDto;
import com.verivue.model.comment.dto.CommentLikeDto;
import com.verivue.model.comment.dto.CommentSaveDto;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Save Comment
     * @param commentSaveDto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult saveComment(@RequestBody CommentSaveDto commentSaveDto) {
        return commentService.saveComment(commentSaveDto);
    }

    /**
     * Get comments by ArticleId
     * @param commentDto
     * @return
     */
    @GetMapping("/load")
    public ResponseResult getCommentsByArticleId(@RequestBody CommentDto commentDto) {
        return commentService.getCommentsByArticleId(commentDto);
    }

    /**
     * Like comment
     * @param commentLikeDto
     * @return
     */
    @PostMapping("/like")
    public ResponseResult likeComment(@RequestBody CommentLikeDto commentLikeDto) {
        return commentService.likeComment(commentLikeDto);
    }
}
