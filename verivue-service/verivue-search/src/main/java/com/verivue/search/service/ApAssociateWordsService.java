package com.verivue.search.service;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.search.dto.UserSearchDto;

public interface ApAssociateWordsService {
    /**
     *  Get Search Associated Words
     * @param userSearchDto
     * @return
     */
    ResponseResult getSearchAssociatedWords(UserSearchDto userSearchDto);
}
