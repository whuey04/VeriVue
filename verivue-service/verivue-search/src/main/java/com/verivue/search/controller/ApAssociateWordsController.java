package com.verivue.search.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.search.dto.UserSearchDto;
import com.verivue.search.service.ApAssociateWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/associate")
public class ApAssociateWordsController {

    @Autowired
    private ApAssociateWordsService apAssociateWordsService;

    /**
     * Get Search Associated Words
     * @param userSearchDto
     * @return
     */
    @PostMapping("/search")
    public ResponseResult getSearchAssociatedWords(@RequestBody UserSearchDto userSearchDto) {
        return apAssociateWordsService.getSearchAssociatedWords(userSearchDto);
    }
}
