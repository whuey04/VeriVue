package com.verivue.wemedia.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.WmUserUpdateDto;
import com.verivue.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wemedia")
public class WmUserController {

    @Autowired
    private WmUserService wmUserService;

    /**
     * Get Current User Details
     * @return
     */
    @GetMapping("/get_details")
    public ResponseResult getWmUserDetails() {
        return wmUserService.getWmUserDetails();
    }

    @GetMapping("/get/{id}")
    public ResponseResult getWmUserById(@PathVariable("id") Long id) {
        return wmUserService.getWmUserDetailsById(id);
    }

    @PutMapping("/update")
    public ResponseResult updateWmUser(@RequestBody WmUserUpdateDto wmUserUpdateDto) {
        return wmUserService.updateWmUser(wmUserUpdateDto);
    }
}
