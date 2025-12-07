package com.verivue.model.wemedia.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("wm_user")
public class WmUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField("ap_user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long apUserId;

    @TableField("ap_author_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long apAuthorId;

    @TableField("name")
    private String name;

    @TableField("password")
    @JsonIgnore
    private String password;

    @TableField("salt")
    @JsonIgnore
    private String salt;

    @TableField("nickname")
    private String nickname;

    @TableField("image")
    private String image;

    @TableField("location")
    private String location;

    @TableField("phone")
    private String phone;

    /**
     * Status
     * 0-Temporary locked 1-Locked 9-Normal
     */
    @TableField("status")
    private Integer status;

    @TableField("email")
    private String email;

    /**
     * Type
     * 0-Personal 1-Business 2-Sub Account
     */
    @TableField("type")
    private Integer type;

    @TableField("score")
    private Integer score;

    @TableField("login_time")
    private Date loginTime;

    @TableField("created_time")
    private Date createdTime;

}