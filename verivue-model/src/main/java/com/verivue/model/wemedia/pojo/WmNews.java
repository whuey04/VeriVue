package com.verivue.model.wemedia.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("wm_news")
public class WmNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField("user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    /**
     * Article Type
     * 0 No image 1 One image 3 More images
     */
    @TableField("type")
    private Short type;

    @TableField("channel_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long channelId;

    @TableField("labels")
    private String labels;

    @TableField("created_time")
    private Date createdTime;

    @TableField("submited_time")
    private Date submitedTime;

    /**
     * Article Status
     * 0-Draft 1-Submitted(Pending Review) 2-Review Failed 3-Manual Review
     * 4-Manual Review Passed  8-Approved (Pending Publish) 9-Published
     */
    @TableField("status")
    private Short status;

    /**
     * Scheduled publish time; null if not scheduled
     */
    @TableField("publish_time")
    private Date publishTime;

    /**
     * Reason for rejection
     */
    @TableField("reason")
    private String reason;

    @TableField("article_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    @TableField("images")
    private String images;

    @TableField("enable")
    private Short enable;

    @Alias("WmNewsStatus")
    public enum Status{
        NORMAL((short)0),
        SUBMIT((short)1),
        FAIL((short)2),
        ADMIN_AUTH((short)3),
        ADMIN_SUCCESS((short)4),
        SUCCESS((short)8),
        PUBLISHED((short)9);

        short code;

        Status(short code){
            this.code = code;
        }

        public short getCode(){
            return this.code;
        }
    }

    @Alias("WmNewsEnable")
    public enum Enable{
        ENABLE((short)0),DISABLE((short)1);
        short code;
        Enable(short code){
            this.code = code;
        }
        public short getCode(){
            return this.code;
        }
    }

}