package com.verivue.model.user.dto;

import lombok.Data;

@Data
public class UserUpdateDto {

    private Long id;

    private String nickname;

    private String password;

    private String email;

    private String phone;

    private Short sex;

    private String image;
}
