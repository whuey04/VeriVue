package com.verivue.wemedia.service;

import com.verivue.model.article.dto.ArticleCommentDto;
import com.verivue.model.comment.dto.CommentConfigDto;
import com.verivue.model.comment.dto.CommentLikeDto;
import com.verivue.model.comment.dto.CommentManageDto;
import com.verivue.model.comment.dto.CommentReplySaveDto;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;

public interface WmCommentManagerService {
    /**
     * Get comment list
     * @param commentManageDto
     * @return
     */
    ResponseResult getCommentList(CommentManageDto commentManageDto);

    /**
     * Get article comments
     * @param articleCommentDto
     * @return
     */
    PageResponseResult getArticleComments(ArticleCommentDto articleCommentDto);

    /**
     * Update comment status
     * @param commentConfigDto
     * @return
     */
    ResponseResult updateCommentStatus(CommentConfigDto commentConfigDto);

    /**
     * Save comment reply
     * @param commentReplySaveDto
     * @return
     */
    ResponseResult saveCommentReply(CommentReplySaveDto commentReplySaveDto);

    /**
     * Like comment
     * @param dto
     * @return
     */
    ResponseResult likeComment(CommentLikeDto dto);

    /**
     * Delete comment
     * @param commentId
     * @return
     */
    ResponseResult deleteComment(String commentId);

    /**
     * Delete comment reply
     * @param commentReplyId
     * @return
     */
    ResponseResult deleteCommentReply(String commentReplyId);
}
