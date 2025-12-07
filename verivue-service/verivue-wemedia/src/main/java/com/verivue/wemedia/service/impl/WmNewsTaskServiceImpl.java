package com.verivue.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.verivue.api.schedule.IScheduleClient;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.TaskTypeEnum;
import com.verivue.model.schedule.dto.Task;
import com.verivue.model.wemedia.pojo.WmNews;
import com.verivue.utils.common.ProtostuffUtil;
import com.verivue.wemedia.service.WmNewsAutoScanService;
import com.verivue.wemedia.service.WmNewsTaskService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class WmNewsTaskServiceImpl implements WmNewsTaskService {

    @Autowired
    private IScheduleClient scheduleClient;
    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;

    /**
     * Add the Article data into task
     *
     * @param id
     * @param publishTime
     */
    @Override
    @Async
    public void addArticleToTask(Long id, Date publishTime) {
        log.info("BEGIN -- Add article to task ing...");

        Task task = new Task();
        task.setExecuteTime(publishTime.getTime());
        task.setTaskType(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType());
        task.setPriority(TaskTypeEnum.NEWS_SCAN_TIME.getPriority());

        WmNews news = new WmNews();
        news.setId(id);

        task.setParameters(ProtostuffUtil.serialize(news));

        scheduleClient.addTask(task);

        log.info("END -- Add article to task ing...");
    }

    /**
     * Process delayed queue messages for article consumption
     */
    @Scheduled(fixedRate = 1000)
    @Override
    @SneakyThrows
    public void processArticlesFromDelayedQueue() {
        log.info("Article review — BEGIN execution of consumption task");

        ResponseResult result = scheduleClient.poll(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType(), TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        if(result.getCode().equals(200) && result.getData() != null) {
            String jsonStr = JSON.toJSONString(result.getData());
            Task task = JSON.parseObject(jsonStr, Task.class);
            byte[] parameters = task.getParameters();
            WmNews news = ProtostuffUtil.deserialize(parameters, WmNews.class);
            System.out.println(news.getId()+"-----------");
            wmNewsAutoScanService.autoScanWmNews(news.getId());
        }
        log.info("Article review — END execution of consumption task");
    }
}
