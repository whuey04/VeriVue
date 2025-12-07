package com.verivue.user.feign;

import com.verivue.api.user.IUserClient;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserClient implements IUserClient {

    @Autowired
    private ApUserService apUserService;

    @Override
    @GetMapping("/api/v1/user/{id}")
    public ApUser getUserById(@PathVariable("id") Long id) {
        return apUserService.getById(id);
    }
}
