package com.verivue.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.wemedia.dto.SensitiveDto;
import com.verivue.model.admin.pojo.AdSensitive;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.pojo.WmSensitive;

public interface WmSensitiveService extends IService<WmSensitive> {
    /**
     * Get List of Sensitive Words
     * @param sensitiveDto
     * @return
     */
    ResponseResult getSensitiveWordsList(SensitiveDto sensitiveDto);

    /**
     * Save Sensitive Word
     * @param adSensitive
     * @return
     */
    ResponseResult saveSensitiveWord(AdSensitive adSensitive);

    /**
     * Update Sensitive Word
     * @param adSensitive
     * @return
     */
    ResponseResult updateSensitiveWord(AdSensitive adSensitive);

    /**
     * Delete Sensitive Word
     * @param id
     * @return
     */
    ResponseResult deleteSensitiveWord(Integer id);

    /**
     * Check Sensitive Word
     * @param content
     * @return
     */
    ResponseResult checkSensitiveWord(String content);
}
