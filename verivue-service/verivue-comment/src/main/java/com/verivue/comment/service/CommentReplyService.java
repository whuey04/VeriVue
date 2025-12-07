package com.verivue.comment.service;

import com.verivue.model.comment.dto.CommentReplyDto;
import com.verivue.model.comment.dto.CommentReplyLikeDto;
import com.verivue.model.comment.dto.CommentReplySaveDto;
import com.verivue.model.common.dto.ResponseResult;

public interface CommentReplyService {
    /**
     * Get and load comment's replies
     * @param commentReplyDto
     * @return
     */
    ResponseResult getCommentReply(CommentReplyDto commentReplyDto);

    /**
     * Save comment's reply
     * @param commentReplySaveDto
     * @return
     */
    ResponseResult saveCommentReply(CommentReplySaveDto commentReplySaveDto);

    /**
     * Save like for the comment's reply
     * @param commentReplyLikeDto
     * @return
     */
    ResponseResult saveCommentReplyLike(CommentReplyLikeDto commentReplyLikeDto);
}
