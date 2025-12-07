package com.verivue.model.wemedia.dto;

import com.verivue.model.common.dto.PageRequestDto;
import lombok.Data;

@Data
public class NewsAuthDto extends PageRequestDto {

    // Article Id
    private Long id;

    // Article title
    private String title;

    // Article Status
    private Integer status;

    // Reject reason
    private String msg;



}
