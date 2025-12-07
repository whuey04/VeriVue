package com.verivue.model.wemedia.dto;

import lombok.Data;

@Data
public class WmUserUpdateDto {

    private Long id;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private String image;

    private Integer type;

}
