package com.verivue.model.comment.dto;

import com.verivue.model.common.dto.PageRequestDto;
import lombok.Data;
 
@Data
public class CommentManageDto extends PageRequestDto {

    private Long articleId;
}
