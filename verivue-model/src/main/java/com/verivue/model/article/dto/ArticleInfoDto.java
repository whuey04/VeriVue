package com.verivue.model.article.dto;

import com.verivue.model.common.annotation.IdEncrypt;
import lombok.Data;
 
@Data
public class ArticleInfoDto {

    // Device ID
    @IdEncrypt
    Integer equipmentId;

    @IdEncrypt
    Long articleId;

    @IdEncrypt
    Long authorId;
}
