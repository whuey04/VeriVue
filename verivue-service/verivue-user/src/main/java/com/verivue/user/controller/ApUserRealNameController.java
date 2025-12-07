package com.verivue.user.controller;

import com.verivue.common.constants.UserVerificationConstants;
import com.verivue.model.user.dto.AuthDto;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.user.dto.UserRealnameDto;
import com.verivue.user.service.ApUserRealNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class ApUserRealNameController {

    @Autowired
    private ApUserRealNameService apUserRealNameService;

    /**
     * Get User Identity Verification List
     * @param authDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getUserVerificationList(@RequestBody AuthDto authDto) {
        return apUserRealNameService.getUserVerificationList(authDto);
    }

    /**
     * Handle User Identity Verification Rejected
     * @param authDto
     * @return
     */
    @PostMapping("/authFail")
    public ResponseResult handleVerificationRejected(@RequestBody AuthDto authDto){
        return apUserRealNameService.handleVerification(authDto, UserVerificationConstants.REJECTED);
    }

    /**
     * Handle User Identity Verification Approved
     * @param authDto
     * @return
     */
    @PostMapping("/authPass")
    public ResponseResult handleVerificationApproved(@RequestBody AuthDto authDto){
        return apUserRealNameService.handleVerification(authDto, UserVerificationConstants.APPROVED);
    }

    //User App

    /**
     * Register WeMedia Account
     * @param userRealnameDto
     * @return
     */
    @PostMapping("/add")
    public ResponseResult registerWeMedia(@RequestBody UserRealnameDto userRealnameDto){
        return apUserRealNameService.registerWeMedia(userRealnameDto);
    }
}
