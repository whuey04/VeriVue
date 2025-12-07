package com.verivue.model.user.dto;

import lombok.Data;

@Data
public class LoginDto {

    /**
     * User phone number
     */
    private String phone;

    /**
     * User login password
     */
    private String password;
}
