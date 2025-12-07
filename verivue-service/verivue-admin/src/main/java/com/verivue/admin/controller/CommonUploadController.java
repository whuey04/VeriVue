package com.verivue.admin.controller;

import com.verivue.admin.service.CommonService;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin/common")
public class CommonUploadController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/upload")
    public ResponseResult uploadFile(MultipartFile file) {
        return commonService.uploadFile(file);
    }
}
