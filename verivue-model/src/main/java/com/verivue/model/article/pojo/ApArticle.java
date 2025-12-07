package com.verivue.model.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Article Information Table.
 * To store the published article
 */
@Data
@TableName("ap_article")
public class ApArticle implements Serializable {

    // Article ID
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //  Article title
    private String title;

    // Author ID
    @TableField("author_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    // Author Name
    @TableField("author_name")
    private String authorName;

    // Channel ID
    @TableField("channel_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long channelId;

    // Channel Name
    @TableField("channel_name")
    private String channelName;

    /**
     * Article layout
     * 0 No image  1 Single image 2 Multiple image
     */
    private Short layout;

    /**
     * Article tags
     * 0 Regular 1 Trending 2 Pinned 3 Premium 4 Big Content Creator's
     */
    private Byte flag;

    /**
     * Article cover images, separated by commas if multiple.
     */
    private String images;

    // Labels, separated by commas if multiple.
    private String labels;

    private Integer likes;

    private Integer collection;

    private Integer comment;

    private Integer views;

    @TableField("created_time")
    private Date createdTime;

    @TableField("publish_time")
    private Date publishTime;

    @TableField("sync_status")
    private Boolean syncStatus;

    private Boolean origin;

    @TableField("static_url")
    private String staticUrl;
}
