package com.verivue.article.controller;

import com.verivue.article.service.ApCollectionService;
import com.verivue.model.article.dto.CollectionBehaviorDto;
import com.verivue.model.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/collection_behavior")
public class ApCollectionController {

    @Autowired
    private ApCollectionService apCollectionService;

    /**
     * Save article as favorite
     * @param collectionBehaviorDto
     * @return
     */
    @PostMapping
    public ResponseResult collectionBehavior(@RequestBody CollectionBehaviorDto collectionBehaviorDto) {
        return apCollectionService.collectionBehavior(collectionBehaviorDto);
    }
}
