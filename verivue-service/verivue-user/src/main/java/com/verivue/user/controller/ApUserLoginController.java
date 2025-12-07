package com.verivue.user.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.user.dto.LoginDto;
import com.verivue.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class ApUserLoginController {

    @Autowired
    private ApUserService apUserService;

    @PostMapping("/app_login")
    public ResponseResult userLogin(@RequestBody LoginDto loginDto){
        return apUserService.userLogin(loginDto);
    }
}
