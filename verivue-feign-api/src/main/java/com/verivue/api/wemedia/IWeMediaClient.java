package com.verivue.api.wemedia;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.pojo.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("verivue-wemedia")
public interface IWeMediaClient {

    /**
     * Find WeMedia User by their username
     * @param name
     * @return
     */
    @GetMapping("/api/v1/user/findByName/{name}")
    WmUser findWmUserByName(@PathVariable("name") String name);

    /**
     * Add new WeMedia User
     * @param wmUser
     * @return
     */
    @PostMapping("/api/v1/wm_user/save")
    ResponseResult addWmUser(@RequestBody WmUser wmUser);

    /**
     * Get Channel List
     * @return
     */
    @GetMapping("/api/v1/channel/list")
    ResponseResult getChannelList();

    /**
     * Check sensitive word in comment
     * @param content
     * @return
     */
    @PostMapping("/api/v1/wm_sensitive/check")
    ResponseResult checkSensitive(@RequestBody String content);
}
