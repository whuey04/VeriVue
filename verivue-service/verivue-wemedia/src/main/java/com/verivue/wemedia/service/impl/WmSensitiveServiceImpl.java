package com.verivue.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.model.wemedia.dto.SensitiveDto;
import com.verivue.model.admin.pojo.AdSensitive;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.wemedia.pojo.WmSensitive;
import com.verivue.utils.common.SensitiveWordUtil;
import com.verivue.wemedia.mapper.WmSensitiveMapper;
import com.verivue.wemedia.service.WmSensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//ADMIN
@Service
@Slf4j
@Transactional
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitiveService {

    @Autowired
    private WmSensitiveMapper sensitiveMapper;
    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;

    /**
     * Get List of Sensitive Words
     *
     * @param sensitiveDto
     * @return
     */
    @Override
    public ResponseResult getSensitiveWordsList(SensitiveDto sensitiveDto) {
        // 1. Check Param
        sensitiveDto.checkParam();

        // 2. Page Query
        IPage page = new Page(sensitiveDto.getPage(), sensitiveDto.getSize());

        LambdaQueryWrapper<WmSensitive> queryWrapper = new LambdaQueryWrapper<>();
        // 3.1 Fuzzy query based on sensitive word
        if (StringUtils.isNotBlank(sensitiveDto.getName())){
            queryWrapper.like(WmSensitive::getSensitives, sensitiveDto.getName());
        }

        // 3.2 Sort by created time (desc)
        queryWrapper.orderByDesc(WmSensitive::getCreatedTime);

        page = page(page, queryWrapper);

        // 4. return result
        ResponseResult result = new PageResponseResult(sensitiveDto.getPage(), sensitiveDto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    /**
     * Save Sensitive Word
     *
     * @param adSensitive
     * @return
     */
    @Override
    public ResponseResult saveSensitiveWord(AdSensitive adSensitive) {
        if (adSensitive == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmSensitive sensitive = new WmSensitive();
        BeanUtils.copyProperties(adSensitive, sensitive);

        // Check if sensitive word exists
        WmSensitive sensitiveCheck = getOne(Wrappers.<WmSensitive>lambdaQuery()
                .eq(WmSensitive::getSensitives, adSensitive.getSensitives()));
        if (sensitiveCheck != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        sensitive.setCreatedTime(new Date());
        save(sensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Update Sensitive Word
     *
     * @param adSensitive
     * @return
     */
    @Override
    public ResponseResult updateSensitiveWord(AdSensitive adSensitive) {
        if (adSensitive == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmSensitive sensitive = new WmSensitive();
        BeanUtils.copyProperties(adSensitive, sensitive);

        updateById(sensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Delete Sensitive Word
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteSensitiveWord(Integer id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmSensitive sensitive = getById(id);
        if (sensitive == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Check Sensitive Word
     *
     * @param content
     * @return
     */
    @Override
    public ResponseResult checkSensitiveWord(String content) {
        log.info("checkSensitiveWord:{}", content);
        // Query all the sensitive words
        List<WmSensitive> sensitives = wmSensitiveMapper.selectList(Wrappers.<WmSensitive>lambdaQuery()
                .select(WmSensitive::getSensitives));
        List<String> sensitiveslist = sensitives.stream().map(WmSensitive::getSensitives).collect(Collectors.toList());

        //Initialize the sensitive database
        SensitiveWordUtil.initMap(sensitiveslist);

        // Check if the comment contains sensitive words
        Map<String, Integer> map = SensitiveWordUtil.matchWords(content);
        if (map.size() > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "This comment contains sensitive word.");
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
