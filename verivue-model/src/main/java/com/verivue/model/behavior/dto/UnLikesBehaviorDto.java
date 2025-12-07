package com.verivue.model.behavior.dto;

import com.verivue.model.common.annotation.IdEncrypt;
import lombok.Data;
 
@Data
public class UnLikesBehaviorDto {

    @IdEncrypt
    Long articleId;

    /**
     * Dislike operation type
     * 0 Dislike 1 Cancel dislike
     */
    Short type;
}
