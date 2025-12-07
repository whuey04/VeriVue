package com.verivue.comment.service;

import com.verivue.model.comment.dto.CommentDto;
import com.verivue.model.comment.dto.CommentLikeDto;
import com.verivue.model.comment.dto.CommentSaveDto;
import com.verivue.model.common.dto.ResponseResult;

public interface CommentService {
    /**
     * Save comment
     * @param commentSaveDto
     * @return
     */
    ResponseResult saveComment(CommentSaveDto commentSaveDto);

    /**
     * Get comments by ArticleId
     * @param commentDto
     * @return
     */
    ResponseResult getCommentsByArticleId(CommentDto commentDto);

    /**
     * Like comment
     * @param commentLikeDto
     * @return
     */
    ResponseResult likeComment(CommentLikeDto commentLikeDto);
}
