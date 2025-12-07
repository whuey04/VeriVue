package com.verivue.wemedia.service;

import java.util.Date;

public interface WmNewsTaskService {

    /**
     * Add the Article data into task
     * @param id
     * @param publishTime
     */
    void addArticleToTask(Long id, Date publishTime);

    /**
     * Process delayed queue messages for article consumption
     */
    void processArticlesFromDelayedQueue();


}
