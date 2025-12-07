package com.verivue.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.admin.dto.AdUserDto;
import com.verivue.model.admin.dto.AdUserLoginDto;
import com.verivue.model.admin.dto.AdUserPageQueryDto;
import com.verivue.model.admin.dto.AdUserUpdateDto;
import com.verivue.model.admin.pojo.AdUser;
import com.verivue.model.common.dto.ResponseResult;

public interface AdUserService extends IService<AdUser> {
    /**
     * Admin Login
     * @param adUserDto
     * @return
     */
    ResponseResult adminLogin(AdUserLoginDto adUserDto);

    /**
     * Add New Admin Account
     * @param userDto
     * @return
     */
    ResponseResult addAdmin(AdUserDto userDto);

    /**
     * Get All Admin in List
     * @param adUserPageQueryDto
     * @return
     */
    ResponseResult getAdminList(AdUserPageQueryDto adUserPageQueryDto);

    /**
     * Get Admin Details by ID
     * @param id
     * @return
     */
    ResponseResult getAdminDetails(Long id);

    /**
     * Update Admin Details
     * @param adUserUpdateDto
     * @return
     */
    ResponseResult updateAdminDetails(AdUserUpdateDto adUserUpdateDto);
}
