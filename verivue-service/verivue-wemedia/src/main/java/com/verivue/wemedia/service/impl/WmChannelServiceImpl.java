package com.verivue.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.model.wemedia.dto.ChannelDto;
import com.verivue.model.admin.pojo.AdChannel;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.wemedia.pojo.WmChannel;
import com.verivue.model.wemedia.pojo.WmNews;
import com.verivue.wemedia.mapper.WmChannelMapper;
import com.verivue.wemedia.mapper.WmNewsMapper;
import com.verivue.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    /**
     * Find All Channel
     *
     * @return
     */
    @Override
    public ResponseResult findAllChannel() {
        return ResponseResult.okResult(list());
    }

    //ADMIN

    /**
     * Get List of Channel in admin side
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult getListChannel(ChannelDto dto) {
        //1. Check Param
        dto.checkParam();

        // 2. Page Query with their condition
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();

        // 3.1 Fuzzy query based on channel name
        if(StringUtils.isNotBlank(dto.getName())){
            queryWrapper.like(WmChannel::getName, dto.getName());
        }

        // 3.2 Sort by created time (desc)
        queryWrapper.orderByDesc(WmChannel::getCreatedTime);

        page = page(page, queryWrapper);

        // 4. return result
        ResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    /**
     * Save Channel in admin side
     *
     * @param channel
     * @return
     */
    @Override
    public ResponseResult saveChannel(AdChannel channel) {
        if (channel == null || channel.getName() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmChannel wmChannel = new WmChannel();
        BeanUtils.copyProperties(channel, wmChannel);

        // Check if channel exists
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WmChannel::getName, wmChannel.getName());
        long count = count(queryWrapper);
        if (count > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        if(wmChannel.getOrd() == null){
            wmChannel.setOrd(1);
        }

        if(wmChannel.getIsDefault() == null){
            wmChannel.setIsDefault(true);
        }

        wmChannel.setCreatedTime(new Date());

        // Save data to database
        save(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "Add new channel success.");
    }

    /**
     * Delete Channel in admin side
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteChannel(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmChannel channel = getById(id);
        if (channel.getStatus()){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Channel is enabled and cannot be deleted");
        }

        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "Delete channel success.");
    }

    /**
     * Update Channel in admin side
     *
     * @param channel
     * @return
     */
    @Override
    public ResponseResult updateChannel(AdChannel channel) {
        if (channel == null || channel.getName() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmChannel wmChannel = new WmChannel();
        BeanUtils.copyProperties(channel, wmChannel);

        // Check if current channel is in use by article
        Long count = wmNewsMapper.selectCount(Wrappers.<WmNews>lambdaQuery()
                .eq(WmNews::getChannelId, channel.getId()));

        if (count > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Channel is used by article");
        }

        updateById(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "Update channel success.");
    }
}
