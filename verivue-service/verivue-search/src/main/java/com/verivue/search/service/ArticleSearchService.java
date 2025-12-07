package com.verivue.search.service;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.search.dto.UserSearchDto;

import java.io.IOException;

public interface ArticleSearchService {
    /**
     * Search Article by ElasticSearch
     * @param dto
     * @return
     */
    ResponseResult searchArticle(UserSearchDto dto) throws IOException;
}
