package com.verivue.comment.service.impl;

import com.verivue.api.user.IUserClient;
import com.verivue.api.wemedia.IWeMediaClient;
import com.verivue.comment.service.CommentReplyService;
import com.verivue.model.comment.dto.CommentReplyDto;
import com.verivue.model.comment.dto.CommentReplyLikeDto;
import com.verivue.model.comment.dto.CommentReplySaveDto;
import com.verivue.model.comment.pojo.ApComment;
import com.verivue.model.comment.pojo.ApCommentReply;
import com.verivue.model.comment.pojo.ApCommentReplyLike;
import com.verivue.model.comment.vo.CommentReplyVo;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentReplyServiceImpl implements CommentReplyService {

    @Autowired
    private IUserClient userClient;
    @Autowired
    private IWeMediaClient weMediaClient;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Get and load comment's replies
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult getCommentReply(CommentReplyDto dto) {
        if (dto == null || dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        int sizeForReply = 20;

        //Load data
        Query allReplies = Query.query(Criteria.where("commentId").is(dto.getCommentId()).and("createdTime").lt(dto.getMinDate()));
        allReplies.with(Sort.by(Sort.Direction.DESC, "createdTime")).limit(sizeForReply);
        List<ApCommentReply> list = mongoTemplate.find(allReplies, ApCommentReply.class);

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.okResult(list);
        }

        // If user logged in
        List<String> ids = list.stream().map(x -> x.getId()).collect(Collectors.toList());
        Query allRepliesWithUserBehavior = Query.query(Criteria.where("commentReplyId").in(ids).and("authorId").is(user.getId()));
        List<ApCommentReplyLike> apCommentReplyLikes = mongoTemplate.find(allRepliesWithUserBehavior, ApCommentReplyLike.class);
        if (apCommentReplyLikes == null || apCommentReplyLikes.size() == 0) {
            return ResponseResult.okResult(list);
        }

        List<CommentReplyVo> commentReplyVoList = new ArrayList<>();
        list.forEach(x -> {
            CommentReplyVo commentReplyVo = new CommentReplyVo();
            BeanUtils.copyProperties(x, commentReplyVo);
            for (ApCommentReplyLike apCommentReplyLike : apCommentReplyLikes) {
                if (x.getId().equals(apCommentReplyLike.getCommentReplyId())) {
                    commentReplyVo.setOperation((short)0);
                    break;
                }
            }
            commentReplyVoList.add(commentReplyVo);
        });

        return ResponseResult.okResult(commentReplyVoList);
    }

    /**
     * Save comment's reply
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveCommentReply(CommentReplySaveDto dto) {
        if (dto == null || StringUtil.isBlank(dto.getContent()) || dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // Check the length of content
        if (dto.getContent().length() > 140){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Comment text must not exceed 140 characters");
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        ResponseResult responseResult = weMediaClient.checkSensitive(dto.getContent());
        if (!responseResult.getCode().equals(200)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Contains sensitive words");
        }

        ApUser apUser = userClient.getUserById(user.getId());
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"Invalid login information");
        }

        ApCommentReply commentReply = new ApCommentReply();
        commentReply.setAuthorId(user.getId());
        commentReply.setContent(dto.getContent());
        commentReply.setCreatedTime(new Date());
        commentReply.setCommentId(dto.getCommentId());
        commentReply.setAuthorName(apUser.getName());
        commentReply.setUpdatedTime(new Date());
        commentReply.setLikes(0);
        mongoTemplate.save(commentReply);

        ApComment apComment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        apComment.setReply(apComment.getReply() + 1);
        mongoTemplate.save(apComment);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Save like for the comment's reply
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveCommentReplyLike(CommentReplyLikeDto dto) {
        if(dto == null || dto.getCommentReplyId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        ApCommentReply commentReply = mongoTemplate.findById(dto.getCommentReplyId(), ApCommentReply.class);

        if(commentReply != null && dto.getOperation() == 0){
            commentReply.setLikes(commentReply.getLikes() + 1);
            mongoTemplate.save(commentReply);

            ApCommentReplyLike apCommentReplyLike = new ApCommentReplyLike();
            apCommentReplyLike.setCommentReplyId(commentReply.getId());
            apCommentReplyLike.setAuthorId(user.getId());
            mongoTemplate.save(apCommentReplyLike);
        }
        else {
            int tmp = commentReply.getLikes() - 1;
            tmp = tmp < 1 ? 0 : tmp;
            commentReply.setLikes(tmp);
            mongoTemplate.save(commentReply);

            Query query = Query.query(Criteria.where("commentReplyId").is(commentReply.getId()).and("authorId").is(user.getId()));
            mongoTemplate.remove(query, ApCommentReplyLike.class);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("likes", commentReply.getLikes());
        return ResponseResult.okResult(result);
    }
}
