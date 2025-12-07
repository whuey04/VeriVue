package com.verivue.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.verivue.model.wemedia.dto.ChannelDto;
import com.verivue.model.admin.pojo.AdChannel;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.pojo.WmChannel;

public interface WmChannelService extends IService<WmChannel> {
    /**
     * Find All Channel
     * @return
     */
    ResponseResult findAllChannel();

    //ADMIN

    /**
     * Get List of Channel in admin side
     * @param dto
     * @return
     */
    ResponseResult getListChannel(ChannelDto dto);

    /**
     * Save Channel in admin side
     * @param channel
     * @return
     */
    ResponseResult saveChannel(AdChannel channel);

    /**
     * Delete Channel in admin side
     * @param id
     * @return
     */
    ResponseResult deleteChannel(Long id);

    /**
     * Update Channel in admin side
     * @param channel
     * @return
     */
    ResponseResult updateChannel(AdChannel channel);
}
