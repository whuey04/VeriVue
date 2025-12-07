package com.verivue.model.article.dto;

import com.verivue.model.common.dto.PageRequestDto;
import lombok.Data;

@Data
public class ArticleCommentDto extends PageRequestDto {

    private String beginDate;
    private String endDate;
    private Long wmUserId;

}
