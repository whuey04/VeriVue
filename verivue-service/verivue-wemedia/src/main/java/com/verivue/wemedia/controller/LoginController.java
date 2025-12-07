package com.verivue.wemedia.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.WmLoginDto;
import com.verivue.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private WmUserService wmUserService;

    @PostMapping
    public ResponseResult login(@RequestBody WmLoginDto loginDto) {
        return wmUserService.login(loginDto);
    }
}
