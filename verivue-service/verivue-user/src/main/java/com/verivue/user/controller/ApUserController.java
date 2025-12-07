package com.verivue.user.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.user.dto.UserDto;
import com.verivue.model.user.dto.UserUpdateDto;
import com.verivue.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class ApUserController {

    @Autowired
    private ApUserService apUserService;

    @PostMapping("/add")
    public ResponseResult addUser(@RequestBody UserDto userDto) {
        return apUserService.addUser(userDto);
    }

    @GetMapping("/get_details")
    public ResponseResult getUserDetails() {
        return apUserService.getUserDetails();
    }

    @GetMapping("/get/{id}")
    public ResponseResult getUserDetailsById(@PathVariable("id") Long id) {
        return apUserService.getUserDetailsById(id);
    }

    @PutMapping("/update")
    public ResponseResult updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return apUserService.updateUser(userUpdateDto);
    }
}
