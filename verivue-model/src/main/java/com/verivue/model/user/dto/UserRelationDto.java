package com.verivue.model.user.dto;

import com.verivue.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class UserRelationDto {

    @IdEncrypt
    private Long articleId;

    @IdEncrypt
    private Long authorId;

    private Short operation;
}
