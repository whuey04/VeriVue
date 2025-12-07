package com.verivue.model.admin.dto;

import lombok.Data;

@Data
public class AdUserUpdateDto {

    private Long id;

    private String nickname;

    private String password;

    private String phone;

    private String email;

    private String image;
}
