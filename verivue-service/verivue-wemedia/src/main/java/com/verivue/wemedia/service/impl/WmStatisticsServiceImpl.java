package com.verivue.wemedia.service.impl;

import com.verivue.api.article.IArticleClient;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.dto.StatisticsDto;
import com.verivue.model.wemedia.pojo.WmUser;
import com.verivue.utils.common.DateUtils;
import com.verivue.utils.thread.WmThreadLocalUtil;
import com.verivue.wemedia.service.WmNewsService;
import com.verivue.wemedia.service.WmStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class WmStatisticsServiceImpl implements WmStatisticsService {
    
    @Autowired
    private WmNewsService wmNewsService;
    @Autowired
    private IArticleClient articleClient;

    @Override
    public ResponseResult newsDimension(String beginDate, String endDate) {
        Map<String, Object> resultMap = new HashMap<>();

        Date beginDateTime = DateUtils.stringToDate(beginDate);
        Date endDateTime = DateUtils.stringToDate(endDate);

        WmUser user = WmThreadLocalUtil.getUser();

        /*int publishNum = wmNewsService.count(Wrappers.<WmNews>lambdaQuery()
                .eq(WmNews::getUserId, user.getId())
                .eq(WmNews::getStatus, WmNews.Status.PUBLISHED.getCode())
                .eq(WmNews::getEnable, 1)
                .between(WmNews::getPublishTime, beginDateTime, endDateTime));
        resultMap.put("publishNum", publishNum);*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String beginDateStr = dateFormat.format(beginDateTime);
        String endDateStr = dateFormat.format(endDateTime);

        ResponseResult responseResult = articleClient.getLikesAndCollections(user.getId(), beginDateStr, endDateStr);
        if(responseResult.getCode().equals(200)) {
//            String res_json = JSON.toJSONString(responseResult.getData());
//            Map map = JSON.parseObject(res_json, Map.class);
            Map<String, Object> map = (Map<String, Object>) responseResult.getData();
            resultMap.put("likesNum", map.get("likesNum") == null ? 0 : map.get("likesNum"));
            resultMap.put("collectNum", map.get("collectNum") == null ? 0 : map.get("collectNum"));
            resultMap.put("publishNum", map.get("publishNum") == null ? 0 : map.get("publishNum"));
        }

        return ResponseResult.okResult(resultMap);
    }

    @Override
    public PageResponseResult newsPage(StatisticsDto dto) {
        WmUser user = WmThreadLocalUtil.getUser();
        dto.setWmUserId(user.getId());
        PageResponseResult responseResult = articleClient.newPage(dto);

        return responseResult;
    }
}
