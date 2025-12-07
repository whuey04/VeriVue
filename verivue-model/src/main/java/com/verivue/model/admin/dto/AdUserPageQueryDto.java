package com.verivue.model.admin.dto;

import com.verivue.model.common.dto.PageRequestDto;
import lombok.Data;

@Data
public class AdUserPageQueryDto extends PageRequestDto {

    private String name;

    private Short status;
}
