package com.verivue.article.job;

import com.verivue.article.service.HotArticleService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ComputeHotArticleJob {

    @Autowired
    private HotArticleService hotArticleService;

    @XxlJob("calculateHotArticleJob")
    public void handle(){
        log.info("Start scheduled task: calculate hot article scores...");
        hotArticleService.calculateHotArticle();
        log.info("End scheduled task: calculate hot article scores...");

    }
}