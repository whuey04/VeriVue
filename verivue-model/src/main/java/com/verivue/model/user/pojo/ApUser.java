package com.verivue.model.user.pojo;

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
@TableName("ap_user")
public class ApUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * User ID - Primary Key
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * Salt value used for password encryption, communication security, etc.
     */
    @TableField("salt")
    @JsonIgnore
    private String salt;

    /**
     * Username
     */
    @TableField("name")
    private String name;

    /**
     * Password (md5)
     */
    @TableField("password")
    @JsonIgnore
    private String password;

    /**
     * Phone number
     */
    @TableField("phone")
    private String phone;

    /**
     * Email
     */
    @TableField("email")
    private String email;

    /**
     * Nickname
     */
    @TableField("nickname")
    private String nickname;

    /**
     * Avatar
     */
    @TableField("image")
    private String image;

    /**
     * Gender - 0 Male, 1 Female, 2 Unknown
     */
    @TableField("sex")
    private Short sex;

    /**
     * Check certification - 0 No, 1 Yes
     */
    @TableField("is_certification")
    private Boolean certification;

    /**
     * Check Identity Authentication
     */
    @TableField("is_identity_authentication")
    private Boolean identityAuthentication;

    /**
     * Account status - 0 Normal, 1 Locked
     */
    @TableField("status")
    private Boolean status;

    /**
     * User Type - 0 Normal User, 1 Content Creator, 2 Big Content Creator
     */
    @TableField("flag")
    private Short flag;

    /**
     * Account created time
     */
    @TableField("created_time")
    private Date createdTime;
}
