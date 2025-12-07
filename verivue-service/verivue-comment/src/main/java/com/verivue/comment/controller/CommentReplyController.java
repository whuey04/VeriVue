package com.verivue.comment.controller;

import com.verivue.model.comment.dto.CommentReplyDto;
import com.verivue.model.comment.dto.CommentReplyLikeDto;
import com.verivue.model.comment.dto.CommentReplySaveDto;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment_reply")
public class CommentReplyController {

    @Autowired
    private com.verivue.comment.service.CommentReplyService commentReplyService;

    /**
     * Get and load comment's replies
     * @param commentReplyDto
     * @return
     */
    @GetMapping("/load")
    public ResponseResult getCommentReply(@RequestBody CommentReplyDto commentReplyDto) {
        return commentReplyService.getCommentReply(commentReplyDto);
    }

    /**
     * Save comment's reply
     * @param commentReplySaveDto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult saveCommentReply(@RequestBody CommentReplySaveDto commentReplySaveDto) {
        return commentReplyService.saveCommentReply(commentReplySaveDto);
    }

    /**
     * Save like for the comment's reply
     * @param commentReplyLikeDto
     * @return
     */
    @PostMapping("/like")
    public ResponseResult saveCommentReplyLike(@RequestBody CommentReplyLikeDto commentReplyLikeDto) {
        return commentReplyService.saveCommentReplyLike(commentReplyLikeDto);
    }

}