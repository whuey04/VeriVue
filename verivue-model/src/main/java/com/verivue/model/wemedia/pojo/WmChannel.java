package com.verivue.model.wemedia.pojo;

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
@TableName("wm_channel")
public class WmChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    /**
     * Is Default Channel
     * 1：Yes 0:No
     */
    @TableField("is_default")
    private Boolean isDefault;

    /**
     * Status
     * 1：true 0：false
     */
    @TableField("status")
    private Boolean status;

    /**
     * Default order
     */
    @TableField("ord")
    private Integer ord;

    @TableField("created_time")
    private Date createdTime;

}