package com.verivue.article.service;

import com.verivue.model.article.dto.CollectionBehaviorDto;
import com.verivue.model.common.dto.ResponseResult;

public interface ApCollectionService {
    /**
     * Save article as favorite
     * @param collectionBehaviorDto
     * @return
     */
    ResponseResult collectionBehavior(CollectionBehaviorDto collectionBehaviorDto);
}
