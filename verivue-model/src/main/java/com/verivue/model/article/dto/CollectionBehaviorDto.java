package com.verivue.model.article.dto;

import com.verivue.model.common.annotation.IdEncrypt;
import lombok.Data;
 
import java.util.Date;
 
@Data
public class CollectionBehaviorDto {

    @IdEncrypt
    Long entryId;

    Short type;

    /**
     * Operation type:
     * 0 - Favorite 1 - Cancel favorite
     */
    Short operation;

    Date publishedTime;
}
 
