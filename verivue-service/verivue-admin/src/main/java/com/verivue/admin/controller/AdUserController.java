package com.verivue.admin.controller;

import com.verivue.admin.service.AdUserService;
import com.verivue.model.admin.dto.AdUserDto;
import com.verivue.model.admin.dto.AdUserPageQueryDto;
import com.verivue.model.admin.dto.AdUserUpdateDto;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdUserController {

    @Autowired
    private AdUserService adUserService;

    /**
     * Add new admin
     * @param userDto
     * @return
     */
    @PostMapping("/add")
    public ResponseResult addAdmin(@RequestBody AdUserDto userDto) {
        return adUserService.addAdmin(userDto);
    }

    /**
     * Get all admin
     * @param adUserPageQueryDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getAdminList(@RequestBody AdUserPageQueryDto adUserPageQueryDto) {
        return adUserService.getAdminList(adUserPageQueryDto);
    }

    /**
     * Get Admin Details by ID
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    public ResponseResult getAdminDetails(@PathVariable("id") Long id) {
        return adUserService.getAdminDetails(id);
    }

    /**
     * Update Admin Details
     * @param adUserUpdateDto
     * @return
     */
    @PutMapping("/update")
    public ResponseResult updateAdminDetails(@RequestBody AdUserUpdateDto adUserUpdateDto) {
        return adUserService.updateAdminDetails(adUserUpdateDto);
    }
}
