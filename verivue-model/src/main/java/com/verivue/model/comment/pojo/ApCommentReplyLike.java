package com.verivue.model.comment.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("ap_comment_reply_like")
public class ApCommentReplyLike {


    private String id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    private String commentReplyId;

    private Short operation;
}