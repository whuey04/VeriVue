package com.verivue.model.wemedia.dto;

import com.verivue.model.common.dto.PageRequestDto;
import lombok.Data;

@Data
public class SensitiveDto extends PageRequestDto {

    private String name;
}
