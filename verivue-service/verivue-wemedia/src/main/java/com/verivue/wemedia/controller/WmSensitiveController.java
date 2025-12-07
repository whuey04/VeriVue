package com.verivue.wemedia.controller;

import com.verivue.model.wemedia.dto.SensitiveDto;
import com.verivue.model.admin.pojo.AdSensitive;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.wemedia.service.WmSensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensitive")
public class WmSensitiveController {

    @Autowired
    private WmSensitiveService wmSensitiveService;

    //ADMIN

    /**
     * Get List of Sensitive Words
     * @param sensitiveDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getSensitiveWordsList(@RequestBody SensitiveDto sensitiveDto) {
        return wmSensitiveService.getSensitiveWordsList(sensitiveDto);
    }

    /**
     * Save Sensitive Word
     * @param adSensitive
     * @return
     */
    @PostMapping("/save")
    public ResponseResult saveSensitiveWord(@RequestBody AdSensitive adSensitive) {
        return wmSensitiveService.saveSensitiveWord(adSensitive);
    }

    /**
     * Update Sensitive Word
     * @param adSensitive
     * @return
     */
    @PutMapping("/update")
    public ResponseResult updateSensitiveWord(@RequestBody AdSensitive adSensitive) {
        return wmSensitiveService.updateSensitiveWord(adSensitive);
    }

    /**
     * Delete Sensitive Word
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    private ResponseResult deleteSensitiveWord(@PathVariable("id") Integer id) {
        return wmSensitiveService.deleteSensitiveWord(id);
    }
}
