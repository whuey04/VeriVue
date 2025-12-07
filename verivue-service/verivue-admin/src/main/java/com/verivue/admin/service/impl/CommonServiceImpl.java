package com.verivue.admin.service.impl;

import com.verivue.admin.service.CommonService;

import com.verivue.file.service.FileStorageService;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Upload Img File
     *
     * @param file
     * @return
     */
    @Override
    public ResponseResult uploadFile(MultipartFile file) {
        if (file == null || file.getSize() == 0)  {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // Upload Picture to MinIO
        String fileName = UUID.randomUUID().toString().replace("-","");
        String originalFilename = file.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filePath = null;
        try {
            filePath = fileStorageService.uploadImgFile("",fileName+postfix,file.getInputStream());
            log.info("Upload picture to MinIO, filePath:{}",filePath);
        }catch (IOException e){
            e.printStackTrace();
            log.error("CommonServiceImpl-Upload picture to MinIO failed");
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(), "Upload picture to MinIO failed");
        }

        return ResponseResult.okResult(filePath);
    }
}
