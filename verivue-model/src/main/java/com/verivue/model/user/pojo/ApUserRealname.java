package com.verivue.model.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ap_user_realname")
public class ApUserRealname implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @TableField("name")
    private String name;

    @TableField("idno")
    private String idno;

    @TableField("type")
    private Integer type;

    @TableField("font_image")
    private String fontImage;

    @TableField("back_image")
    private String backImage;

    @TableField("hold_image")
    private String holdImage;

    @TableField("live_image")
    private String liveImage;

    /**
     * Article status codes:
     * 0 - Creating
     * 1 - Pending review
     * 2 - Review failed
     * 9 - Review approved
     */
    @TableField("status")
    private Short status;

    @TableField("reason")
    private String reason;

    @TableField("created_time")
    private Date createdTime;

    @TableField("submited_time")
    private Date submitedTime;

    @TableField("updated_time")
    private Date updatedTime;

}