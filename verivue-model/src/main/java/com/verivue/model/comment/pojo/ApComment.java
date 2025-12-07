package com.verivue.model.comment.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document("ap_comment")
public class ApComment {


    private String id;

    /**
     * User ID - person who posted comments
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    private String authorName;

    /**
     * Article ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long entryId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long channelId;

    private Short type;

    private String content;

    private String image;

    private Integer likes;

    private Integer reply;

    /**
     * Comment Type
     * 0 Normal
     * 1 Hot
     * 2 Recommended
     * 3 Pinned
     * 4 Featured
     * 5 Influencer comment
     */
    private Short flag;

    private Integer ord;

    private Date createdTime;

    private Date updatedTime;

    /**
     * Comment Status
     * 0 Close 1 Normal
     */
    private Boolean status;
}
