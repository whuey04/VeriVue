package com.verivue.model.comment.dto;

import com.verivue.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class CommentSaveDto {

    @IdEncrypt
    private Long articleId;

    public String content;
}
