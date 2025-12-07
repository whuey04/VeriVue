package com.verivue.model.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * Configuration of article
 */
@Data
@TableName("ap_article_config")
public class ApArticleConfig implements Serializable {

    public ApArticleConfig(Long articleId){
        this.articleId = articleId;
        this.isComment = true;
        this.isForward = true;
        this.isDelete = false;
        this.isDown = false;
    }

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField("article_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    /**
     * Is Comment?
     * 1 - can comment; 0 - cannot comment
     */
    @TableField("is_comment")
    private Boolean isComment;

    /**
     * Is forward?
     * 1 - can;  0 - cannot
     */
    @TableField("is_forward")
    private Boolean isForward;

    /**
     * Is Remove?
     * 1 - yes;  0 - no
     */
    @TableField("is_down")
    private Boolean isDown;

    /**
     * Is Delete?
     * 1 - yes;  0 - no
     */
    @TableField("is_delete")
    private Boolean isDelete;
}
