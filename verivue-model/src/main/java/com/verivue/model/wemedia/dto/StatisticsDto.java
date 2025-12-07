package com.verivue.model.wemedia.dto;

import com.verivue.model.common.dto.PageRequestDto;
import lombok.Data;

@Data
public class StatisticsDto extends PageRequestDto {

    private String beginDate;
    private String endDate;
    private Long wmUserId;

}
