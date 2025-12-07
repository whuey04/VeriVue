package com.verivue.admin.controller;

import com.verivue.admin.service.AdUserService;
import com.verivue.model.admin.dto.AdUserLoginDto;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AdUserLoginController {

    @Autowired
    private AdUserService adUserService;

    /**
     * Login
     * @param adUserDto
     * @return
     */
    @PostMapping
    public ResponseResult adminLogin(@RequestBody AdUserLoginDto adUserDto) {
        return adUserService.adminLogin(adUserDto);
    }
}
