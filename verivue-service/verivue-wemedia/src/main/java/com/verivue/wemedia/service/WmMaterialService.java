package com.verivue.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.WmMaterialDto;
import com.verivue.model.wemedia.pojo.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {
    /**
     * Upload Picture
     * @param multipartFile
     * @return
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * Get Material List
     * @param wmMaterialDto
     * @return
     */
    ResponseResult getMaterialList(WmMaterialDto wmMaterialDto);

    /**
     * Delete Material / image by id
     * @param id
     * @return
     */
    ResponseResult deleteMaterial(Integer id);

    /**
     * Add or cancel favorite
     * @param id
     * @return
     */
    ResponseResult favoriteOrCancel(Integer id);
}
