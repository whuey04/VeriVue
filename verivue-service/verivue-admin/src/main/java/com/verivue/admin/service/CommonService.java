package com.verivue.admin.service;

import com.verivue.model.common.dto.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
    /**
     * Upload Img File
     *
     * @param file
     * @return
     */
    ResponseResult uploadFile(MultipartFile file);
}
