package com.verivue.wemedia.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.verivue.api.wemedia.IWeMediaClient;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.wemedia.pojo.WmUser;
import com.verivue.wemedia.service.WmChannelService;
import com.verivue.wemedia.service.WmSensitiveService;
import com.verivue.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeMediaClient implements IWeMediaClient {

    @Autowired
    private WmUserService wmUserService;
    @Autowired
    private WmChannelService wmChannelService;
    @Autowired
    private WmSensitiveService wmSensitiveService;

    /**
     * Find WeMedia User by their username
     *
     * @param name
     * @return
     */
    @GetMapping("/api/v1/user/findByName/{name}")
    @Override
    public WmUser findWmUserByName(@PathVariable("name") String name) {
        return wmUserService.getOne(Wrappers.<WmUser>lambdaQuery()
                .eq(WmUser::getName, name));
    }

    /**
     * Add new WeMedia User
     *
     * @param wmUser
     * @return
     */
    @PostMapping("/api/v1/wm_user/save")
    @Override
    public ResponseResult addWmUser(@RequestBody WmUser wmUser) {
        wmUserService.save(wmUser);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Get Channel List
     *
     * @return
     */
    @GetMapping("/api/v1/channel/list")
    @Override
    public ResponseResult getChannelList() {
        return wmChannelService.findAllChannel();
    }

    /**
     * Check sensitive word in comment
     *
     * @param content
     * @return
     */
    @Override
    @PostMapping("/api/v1/wm_sensitive/check")
    public ResponseResult checkSensitive(@RequestBody String content) {
        return wmSensitiveService.checkSensitiveWord(content);
    }
}
