package com.verivue.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.file.service.FileStorageService;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.wemedia.dto.WmMaterialDto;
import com.verivue.model.wemedia.pojo.WmMaterial;
import com.verivue.model.wemedia.pojo.WmNewsMaterial;
import com.verivue.utils.thread.WmThreadLocalUtil;
import com.verivue.wemedia.mapper.WmMaterialMapper;
import com.verivue.wemedia.mapper.WmNewsMaterialMapper;
import com.verivue.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    /**
     * Upload Picture
     *
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        if(multipartFile == null || multipartFile.getSize() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // Upload Picture to MinIO
        String fileName = UUID.randomUUID().toString().replace("-","");
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadImgFile("",fileName+postfix,multipartFile.getInputStream());
            log.info("Upload picture to MinIO, fileId:{}",fileId);
        }catch (IOException e){
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-Upload picture to MinIO failed");
        }

        //Save to database
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(WmThreadLocalUtil.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short)0);
        wmMaterial.setType((short)0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);

        return ResponseResult.okResult(wmMaterial);
    }

    /**
     * Get Material List
     *
     * @param wmMaterialDto
     * @return
     */
    @Override
    public ResponseResult getMaterialList(WmMaterialDto wmMaterialDto) {
        wmMaterialDto.checkParam();

        // Page Query
        IPage page = new Page(wmMaterialDto.getPage(), wmMaterialDto.getSize());
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // Check "Is collection" ?
        if(wmMaterialDto.getIsCollection() != null && wmMaterialDto.getIsCollection() == 1){
            lambdaQueryWrapper.eq(WmMaterial::getIsCollection, wmMaterialDto.getIsCollection());
        }

        // Query by userId
        lambdaQueryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtil.getUser().getId());

        lambdaQueryWrapper.orderByDesc(WmMaterial::getCreatedTime);

        page = page(page, lambdaQueryWrapper);

        // Return result
        ResponseResult result = new PageResponseResult(wmMaterialDto.getPage(), wmMaterialDto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    /**
     * Delete Material / image by id
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteMaterial(Integer id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //Determine whether the data exists
        WmMaterial material = getById(id);
        if (material == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //Determine whether the file is referenced
        List<WmNewsMaterial> materialList = wmNewsMaterialMapper.selectList(Wrappers.<WmNewsMaterial>lambdaQuery()
                .eq(WmNewsMaterial::getMaterialId, material.getId()));
        if (materialList != null && materialList.size() != 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "File deletion failed");
        }

        //Delete successfully, and delete the data in MinIO
        fileStorageService.delete(material.getUrl());
        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Add or cancel favorite
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult favoriteOrCancel(Integer id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmMaterial material = getById(id);
        if (material == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        // Already favorited
        if (material.getIsCollection() == 1) {
            material.setIsCollection((short)0);
            updateById(material);
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        material.setIsCollection((short)1);
        updateById(material);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
