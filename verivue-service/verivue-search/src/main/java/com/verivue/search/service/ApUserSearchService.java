package com.verivue.search.service;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.search.dto.HistorySearchDto;

public interface ApUserSearchService {

    /**
     * Save User's search history
     * @param keyword
     * @param userId
     */
    void saveUserSearchHistory(String keyword, Long userId);

    /**
     * Get User's search history
     * @return
     */
    ResponseResult getUserSearchHistory();

    /**
     * Delete User's search history
     * @param dto
     * @return
     */
    ResponseResult deleteUserSearchHistory(HistorySearchDto dto);
}
