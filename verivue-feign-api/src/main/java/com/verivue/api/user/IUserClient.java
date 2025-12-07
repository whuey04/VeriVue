package com.verivue.api.user;

import com.verivue.model.user.pojo.ApUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("verivue-user")
public interface IUserClient {

    @GetMapping("/api/v1/user/{id}")
    ApUser getUserById(@PathVariable("id") Long id);

}
