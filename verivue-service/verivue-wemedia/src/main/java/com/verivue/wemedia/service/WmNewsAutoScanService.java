package com.verivue.wemedia.service;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.pojo.WmNews;

public interface WmNewsAutoScanService {

    void autoScanWmNews(Long id);

    ResponseResult saveAppArticle(WmNews news);
}
