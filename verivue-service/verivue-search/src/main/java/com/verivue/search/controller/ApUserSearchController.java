package com.verivue.search.controller;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.search.dto.HistorySearchDto;
import com.verivue.search.service.ApUserSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/history")
public class ApUserSearchController {

    @Autowired
    private ApUserSearchService apUserSearchService;

    /**
     * Get User's search history
     * @return
     */
    @GetMapping("/load")
    public ResponseResult getUserSearchHistory() {
        return apUserSearchService.getUserSearchHistory();
    }

    /**
     * Delete User's search history
     * @param dto
     * @return
     */
    @PostMapping("/del")
    public ResponseResult deleteUserSearchHistory(@RequestBody HistorySearchDto dto) {
        System.out.println("收到的 dto = " + dto);
        return apUserSearchService.deleteUserSearchHistory(dto);
    }

}
