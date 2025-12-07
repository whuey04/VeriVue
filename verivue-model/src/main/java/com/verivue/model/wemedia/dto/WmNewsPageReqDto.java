package com.verivue.model.wemedia.dto;

import com.verivue.model.common.dto.PageRequestDto;
import lombok.Data;

import java.util.Date;

@Data
public class WmNewsPageReqDto extends PageRequestDto {

    private Short status;

    private Date beginPubDate;

    private Date endPubDate;

    private Long channelId;

    private String keyword;
}