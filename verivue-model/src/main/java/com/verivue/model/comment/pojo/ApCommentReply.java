package com.verivue.model.comment.pojo;
 
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
 
import java.util.Date;

@Data
@Document("ap_comment_reply")
public class ApCommentReply {

    @Id
    private String id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    private String authorName;

    private String commentId;

    private String content;

    private Integer likes;

    private Date createdTime;

    private Date updatedTime;
}