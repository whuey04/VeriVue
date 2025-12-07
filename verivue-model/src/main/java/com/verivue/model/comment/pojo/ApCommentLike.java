package com.verivue.model.comment.pojo;
 
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("ap_comment_like")
public class ApCommentLike {

    /**
     * id
     */
    private String id;

    /**
     * Author ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    /**
     * Comment id
     */
    private String commentId;

    /**
     * 0：Like
     * 1：Cancel Like
     */
    private Short operation;
}