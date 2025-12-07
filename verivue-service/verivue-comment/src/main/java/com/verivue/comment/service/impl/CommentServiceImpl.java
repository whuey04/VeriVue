package com.verivue.comment.service.impl;

import com.alibaba.fastjson.JSON;
import com.verivue.api.article.IArticleClient;
import com.verivue.api.user.IUserClient;
import com.verivue.api.wemedia.IWeMediaClient;
import com.verivue.comment.service.CommentService;
import com.verivue.common.constants.HotArticleConstants;
import com.verivue.model.article.pojo.ApArticleConfig;
import com.verivue.model.comment.dto.CommentDto;
import com.verivue.model.comment.dto.CommentLikeDto;
import com.verivue.model.comment.dto.CommentSaveDto;
import com.verivue.model.comment.pojo.ApComment;
import com.verivue.model.comment.pojo.ApCommentLike;
import com.verivue.model.comment.vo.CommentVo;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.mess.UpdateArticleMess;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private IUserClient userClient;
    @Autowired
    private IArticleClient articleClient;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private IWeMediaClient weMediaClient;

    /**
     * Save comment
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveComment(CommentSaveDto dto) {
        if (dto == null
                || StringUtils.isBlank(dto.getContent())
                || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // Check whether the article can be commented on
        if(!checkParam(dto.getArticleId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Comment function in this article is closed");
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

        ApComment apComment = new ApComment();
        apComment.setAuthorId(user.getId());
        apComment.setContent(dto.getContent());
        apComment.setCreatedTime(new Date());
        apComment.setEntryId(dto.getArticleId());
        apComment.setImage(apUser.getImage());
        apComment.setAuthorName(apUser.getName());
        apComment.setLikes(0);
        apComment.setReply(0);
        apComment.setType((short) 0);
        apComment.setFlag((short) 0);
        mongoTemplate.save(apComment);

        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(dto.getArticleId());
        mess.setAdd(1);
        mess.setType(UpdateArticleMess.UpdateArticleType.COMMENT);
        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(mess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    private boolean checkParam(Long articleId) {
        ResponseResult config = articleClient.getArticleConfigByArticleId(articleId);
        if(!config.getCode().equals(200) || config.getData() == null) {
            return false;
        }

        ApArticleConfig articleConfig = JSON.parseObject(JSON.toJSONString(config.getData()), ApArticleConfig.class);
        if(articleConfig == null || !articleConfig.getIsComment()) {
            return false;
        }

        return true;
    }

    /**
     * Get comments by ArticleId
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult getCommentsByArticleId(CommentDto dto) {
        if(dto == null || dto.getArticleId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        int sizeOfComment = 10;

        // Load the comment data
        Query query = Query.query(Criteria.where("entryId")
                .is(dto.getArticleId())
                .and("createdTime")
                .lt(dto.getMinDate()));
        query.with(Sort.by(Sort.Direction.DESC, "createdTime")).limit(sizeOfComment);
        List<ApComment> list = mongoTemplate.find(query, ApComment.class);

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            // If user no login, return all the comment
            return ResponseResult.okResult(list);
        }

        // If user logged in, query comments that user likes
        List<String> ids = list.stream().map(x -> x.getId()).collect(Collectors.toList());
        Query query1 = Query.query(Criteria.where("commentId").in(ids).and("authorId").is(user.getId()));
        List<ApCommentLike> apCommentLikes = mongoTemplate.find(query1, ApCommentLike.class);
        if(apCommentLikes == null) {
            return ResponseResult.okResult(list);
        }

        List<CommentVo> resultList = new ArrayList<>();
        list.forEach(x -> {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(x, vo);
            for (ApCommentLike apCommentLike : apCommentLikes) {
                if(x.getId().equals(apCommentLike.getCommentId())) {
                    vo.setOperation((short) 0);
                    break;
                }
            }
            resultList.add(vo);
        });
        return ResponseResult.okResult(resultList);
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

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        // Find comment by id
        ApComment comment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);

        // Like the comment
        if (comment != null && dto.getOperation() == 0){
            comment.setLikes(comment.getLikes() + 1);
            mongoTemplate.save(comment);

            ApCommentLike commentLike = new ApCommentLike();
            commentLike.setCommentId(comment.getId());
            commentLike.setAuthorId(user.getId());
            mongoTemplate.save(commentLike);
        }else {
            // Cancel like
            int tmp = comment.getLikes() - 1;
            tmp = tmp < 1 ? 0 : tmp;
            comment.setLikes(tmp);
            mongoTemplate.save(comment);

            Query query = Query.query(Criteria.where("commentId").is(comment.getId()).and("authorId").is(user.getId()));
            mongoTemplate.remove(query, ApCommentLike.class);
        }

        //Return result
        Map<String, Object> result = new HashMap<>();
        result.put("likes", comment.getLikes());
        return ResponseResult.okResult(result);
    }
}
