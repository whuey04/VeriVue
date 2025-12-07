package com.verivue.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.WmLoginDto;
import com.verivue.model.wemedia.dto.WmUserUpdateDto;
import com.verivue.model.wemedia.pojo.WmUser;

public interface WmUserService extends IService<WmUser> {
    /**
     * WeMedia Login
     * @param loginDto
     * @return
     */
    ResponseResult login(WmLoginDto loginDto);

    /**
     * Get Current User Details
     * @return
     */
    ResponseResult getWmUserDetails();

    /**
     * Get User Details by ID
     * @param id
     * @return
     */
    ResponseResult getWmUserDetailsById(Long id);

    /**
     * Update User Details
     * @param wmUserUpdateDto
     * @return
     */
    ResponseResult updateWmUser(WmUserUpdateDto wmUserUpdateDto);
}
