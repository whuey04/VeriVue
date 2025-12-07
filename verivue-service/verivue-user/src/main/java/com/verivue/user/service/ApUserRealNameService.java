package com.verivue.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.user.dto.AuthDto;
import com.verivue.model.user.dto.UserRealnameDto;
import com.verivue.model.user.pojo.ApUserRealname;
import com.verivue.model.common.dto.ResponseResult;

public interface ApUserRealNameService extends IService<ApUserRealname> {
    /**
     * Get User Identity Verification List
     * @param authDto
     * @return
     */
    ResponseResult getUserVerificationList(AuthDto authDto);


    /**
     *  Handle User Identity Verification Rejected or Approved
     * @param authDto
     * @param status
     * @return
     */
    ResponseResult handleVerification(AuthDto authDto, Short status);

    /**
     * Register WeMedia account
     * @param userRealnameDto
     * @return
     */
    ResponseResult registerWeMedia(UserRealnameDto userRealnameDto);
}
