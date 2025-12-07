package com.verivue.wemedia.controller;

import com.verivue.model.wemedia.dto.ChannelDto;
import com.verivue.model.admin.pojo.AdChannel;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.wemedia.service.WmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    @Autowired
    private WmChannelService wmChannelService;

    @GetMapping("/channels")
    public ResponseResult findAll(){
        return wmChannelService.findAllChannel();
    }

    // In Admin Side

    /**
     * List all channel in admin side
     * @param dto
     * @return
     */
    @GetMapping("/listChannel")
    public ResponseResult getListChannel(@RequestBody ChannelDto dto){
        return wmChannelService.getListChannel(dto);
    }

    /**
     * Save new Channel in admin side
     * @param channel
     * @return
     */
    @PostMapping("/save")
    public ResponseResult saveChannel(@RequestBody AdChannel channel){
        return wmChannelService.saveChannel(channel);
    }

    /**
     * Delete Channel in admin side
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public ResponseResult deleteChannel(@PathVariable("id") Long id){
        return wmChannelService.deleteChannel(id);
    }

    /**
     * Update Channel in admin side
     * @param channel
     * @return
     */
    @PutMapping("/update")
    public ResponseResult updateChannel(@RequestBody AdChannel channel){
        return wmChannelService.updateChannel(channel);
    }
}