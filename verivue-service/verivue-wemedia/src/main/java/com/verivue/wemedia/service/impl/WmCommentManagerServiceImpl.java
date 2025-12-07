package com.verivue.wemedia.service.impl;


import com.verivue.api.article.IArticleClient;
import com.verivue.api.user.IUserClient;
import com.verivue.api.wemedia.IWeMediaClient;
import com.verivue.model.article.dto.ArticleCommentDto;
import com.verivue.model.comment.dto.CommentConfigDto;
import com.verivue.model.comment.dto.CommentLikeDto;
import com.verivue.model.comment.dto.CommentManageDto;
import com.verivue.model.comment.dto.CommentReplySaveDto;
import com.verivue.model.comment.pojo.ApComment;
import com.verivue.model.comment.pojo.ApCommentLike;
import com.verivue.model.comment.pojo.ApCommentReply;
import com.verivue.model.comment.vo.CommentReplyListVo;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.model.wemedia.pojo.WmUser;
import com.verivue.utils.thread.WmThreadLocalUtil;
import com.verivue.wemedia.mapper.WmUserMapper;
import com.verivue.wemedia.service.WmCommentManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class WmCommentManagerServiceImpl implements WmCommentManagerService {

    @Autowired
    private IArticleClient articleClient;
    @Autowired
    private IUserClient userClient;
    @Autowired
    private IWeMediaClient weMediaClient;
    @Autowired
    private WmUserMapper wmUserMapper;
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * Get comment list
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult getCommentList(CommentManageDto dto) {
        if(dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        dto.checkParam();

        List<CommentReplyListVo> commentReplyListVoList = new ArrayList<>();

        Query query = Query.query(Criteria.where("entryId").is(dto.getArticleId()));
        Pageable pageable = PageRequest.of(dto.getPage()-1, dto.getSize());
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "createdTime"));

        List<ApComment> commentList = mongoTemplate.find(query, ApComment.class);

        for (ApComment comment : commentList) {
            CommentReplyListVo commentReplyListVo = new CommentReplyListVo();
            commentReplyListVo.setApComments(comment);
            Query query1 = Query.query(Criteria.where("commentId").is(comment.getId()));
            query1.with(Sort.by(Sort.Direction.DESC, "createdTime"));

            List<ApCommentReply> apCommentReplies = mongoTemplate.find(query1, ApCommentReply.class);
            commentReplyListVo.setApCommentReplies(apCommentReplies);
            commentReplyListVoList.add(commentReplyListVo);
        }

        return ResponseResult.okResult(commentReplyListVoList);
    }

    /**
     * Get article comments
     *
     * @param dto
     * @return
     */
    @Override
    public PageResponseResult getArticleComments(ArticleCommentDto dto) {
        WmUser user = WmThreadLocalUtil.getUser();
        dto.setWmUserId(user.getId());

        return articleClient.getArticleComments(dto);
    }

    /**
     * Update comment status (Open or Close the comment func)
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateCommentStatus(CommentConfigDto dto) {
//        WmUser wmuser = WmThreadLocalUtil.getUser();
//
//        WmUser user = wmUserMapper.selectById(wmuser.getId());
//        Integer apUserId = user.getApUserId();

        return articleClient.updateCommentStatus(dto);

    }

    /**
     * Save comment reply
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveCommentReply(CommentReplySaveDto dto) {
        if (dto == null
                || StringUtils.isBlank(dto.getContent())
                || dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if (dto.getContent().length() > 140){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Comment text must not exceed 140 characters");
        }

        WmUser wmUser = WmThreadLocalUtil.getUser();
        WmUser user = wmUserMapper.selectById(wmUser.getId());
        if(user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        ApUser apUser = userClient.getUserById(user.getApUserId());

        ApCommentReply commentReply = new ApCommentReply();
        commentReply.setAuthorId(apUser.getId());
        commentReply.setAuthorName(apUser.getName());
        commentReply.setContent(dto.getContent());
        commentReply.setCreatedTime(new Date());
        commentReply.setCommentId(dto.getCommentId());
        commentReply.setUpdatedTime(new Date());
        commentReply.setLikes(0);
        mongoTemplate.save(commentReply);

        ApComment comment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        comment.setReply(comment.getReply() + 1);
        mongoTemplate.save(comment);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Like comment
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult likeComment(CommentLikeDto dto) {
        if (dto == null || dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApComment comment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);

        WmUser wmUser = WmThreadLocalUtil.getUser();
        WmUser user = wmUserMapper.selectById(wmUser.getId());
        if(user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        ApUser apUser = userClient.getUserById(user.getApUserId());

        if (comment != null && dto.getOperation() == 0){
            comment.setLikes(comment.getLikes() + 1);
            mongoTemplate.save(comment);

            ApCommentLike commentLike = new ApCommentLike();
            commentLike.setCommentId(comment.getId());
            commentLike.setAuthorId(apUser.getId());
            mongoTemplate.save(commentLike);
        }else {
            // Cancel like
            int tmp = comment.getLikes() - 1;
            tmp = tmp < 1 ? 0 : tmp;
            comment.setLikes(tmp);
            mongoTemplate.save(comment);

            Query query = Query.query(Criteria.where("commentId").is(comment.getId()).and("authorId").is(apUser.getId()));
            mongoTemplate.remove(query, ApCommentLike.class);
        }

        //Return result
        Map<String, Object> result = new HashMap<>();
        result.put("likes", comment.getLikes());
        return ResponseResult.okResult(result);
    }

    /**
     * Delete comment
     *
     * @param commentId
     * @return
     */
    @Override
    public ResponseResult deleteComment(String commentId) {
        if (StringUtils.isBlank(commentId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        mongoTemplate.remove(Query.query(Criteria.where("id").is(commentId)), ApComment.class);

        mongoTemplate.remove(Query.query((Criteria.where("commentId").is(commentId))), ApCommentReply.class);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Delete comment reply
     *
     * @param commentReplyId
     * @return
     */
    @Override
    public ResponseResult deleteCommentReply(String commentReplyId) {
        if (StringUtils.isBlank(commentReplyId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        mongoTemplate.remove(Query.query(Criteria.where("id").is(commentReplyId)), ApCommentReply.class);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
