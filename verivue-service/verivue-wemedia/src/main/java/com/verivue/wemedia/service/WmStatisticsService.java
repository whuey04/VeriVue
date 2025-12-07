package com.verivue.wemedia.service;

import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.StatisticsDto;

public interface WmStatisticsService {

    ResponseResult newsDimension(String beginDate, String endDate);

    PageResponseResult newsPage(StatisticsDto dto);
}
