package com.verivue.article.listener;

import com.alibaba.fastjson.JSON;
import com.verivue.article.service.ApArticleConfigService;
import com.verivue.common.constants.WmNewsMessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ArticlePublishListener {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    /**
     * Kafka listener for article publish/unpublish events.
     * @param message
     */
    @KafkaListener(topics = WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC)
    public void onArticleStatusEvent(String message) {
        if(StringUtils.isNotBlank(message)) {
            Map map = JSON.parseObject(message, Map.class);
            apArticleConfigService.updateByMap(map);
            log.info("Article configuration updated on the article side, articleId={}", map.get("articleId"));
        }
    }
}
