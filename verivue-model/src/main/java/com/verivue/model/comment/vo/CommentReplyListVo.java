package com.verivue.model.comment.vo;
 
import com.verivue.model.comment.pojo.ApComment;
import com.verivue.model.comment.pojo.ApCommentReply;
import lombok.Data;
 
import java.util.List;
 
@Data
public class CommentReplyListVo {

    private ApComment apComments;

    private List<ApCommentReply> apCommentReplies;
}