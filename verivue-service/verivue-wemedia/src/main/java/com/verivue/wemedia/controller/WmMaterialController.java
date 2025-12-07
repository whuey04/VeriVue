package com.verivue.wemedia.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.WmMaterialDto;
import com.verivue.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    @Autowired
    private WmMaterialService wmMaterialService;

    /**
     * Upload Picture
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        return wmMaterialService.uploadPicture(multipartFile);
    }

    /**
     * Get all material / images
     * @param wmMaterialDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getMaterialList(@RequestBody WmMaterialDto wmMaterialDto) {
        return wmMaterialService.getMaterialList(wmMaterialDto);
    }

    /**
     * Delete Material / image by id
     * @param id
     * @return
     */
    @DeleteMapping("/del_picture/{id}")
    public ResponseResult deleteMaterial(@PathVariable Integer id) {
        return wmMaterialService.deleteMaterial(id);
    }

    /**
     * Add favorite
     * @param id
     * @return
     */
    @PostMapping("/collect/{id}")
    public ResponseResult addFavorite(@PathVariable Integer id){
        return wmMaterialService.favoriteOrCancel(id);
    }

    /**
     * Cancel favorite
     * @param id
     * @return
     */
    @PostMapping("/cancel_collect/{id}")
    public ResponseResult cancelFavorite(@PathVariable Integer id){
        return wmMaterialService.favoriteOrCancel(id);
    }
}
