package com.verivue.model.admin.pojo;

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
@TableName("ad_user")
public class AdUser implements Serializable {

    private static final long serialVersionUID = 1L;

    // Admin ID
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    // Admin Name
    @TableField("name")
    private String name;

    // Password
    @TableField("password")
    @JsonIgnore
    private String password;

    // Salt used for encryption
    @TableField("salt")
    @JsonIgnore
    private String salt;

    // Nickname
    @TableField("nickname")
    private String nickname;

    // Avatar
    @TableField("image")
    private String image;

    // Phone number
    @TableField("phone")
    private String phone;

    /**
     * Status
     * 0: Temporarily unavailable
     * 1: Permanently unavailable
     * 9: Normal
     */
    @TableField("status")
    private Short status;

    // Email address
    @TableField("email")
    private String email;

    // Latest_login_time
    @TableField("latest_login_time")
    private Date latestLoginTime;

    // Created account time
    @TableField("created_time")
    private Date createdTime;

}