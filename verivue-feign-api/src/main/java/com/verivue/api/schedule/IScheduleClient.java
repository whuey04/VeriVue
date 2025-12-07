package com.verivue.api.schedule;

import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.schedule.dto.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("verivue-schedule")
public interface IScheduleClient {

    /**
     * Add task
     * @param task
     * @return
     */
    @PostMapping("/api/v1/task/add")
    ResponseResult addTask(@RequestBody Task task);

    /**
     * Cancel Task
     * @param taskId
     * @return
     */
    @GetMapping("/api/v1/task/cancel/{taskId}")
    ResponseResult cancelTask(@PathVariable("taskId") long taskId);

    /**
     * Pull tasks by type and priority
     * @param type
     * @param priority
     * @return
     */
    @GetMapping("/api/v1/task/poll/{type}/{priority}")
    ResponseResult poll(@PathVariable("type") int type,@PathVariable("priority")  int priority);
}
