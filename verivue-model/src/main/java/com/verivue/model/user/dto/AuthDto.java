package com.verivue.model.user.dto;

import com.verivue.model.common.dto.PageRequestDto;
import lombok.Data;

@Data
public class AuthDto extends PageRequestDto {

    private Integer id;

    private String msg;

    private Short status;
}
