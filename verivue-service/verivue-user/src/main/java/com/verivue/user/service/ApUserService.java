package com.verivue.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.user.dto.LoginDto;
import com.verivue.model.user.dto.UserDto;
import com.verivue.model.user.dto.UserUpdateDto;
import com.verivue.model.user.pojo.ApUser;

public interface ApUserService extends IService<ApUser> {
    /**
     * User Login
     * @param loginDto
     * @return
     */
    ResponseResult userLogin(LoginDto loginDto);

    /**
     * Register user account
     * @param userDto
     * @return
     */
    ResponseResult addUser(UserDto userDto);

    /**
     * Get user details
     * @return
     */
    ResponseResult getUserDetails();

    /**
     * Update
     * @param userUpdateDto
     * @return
     */
    ResponseResult updateUser(UserUpdateDto userUpdateDto);

    /**
     * Get user details by id
     * @param id
     * @return
     */
    ResponseResult getUserDetailsById(Long id);
}
