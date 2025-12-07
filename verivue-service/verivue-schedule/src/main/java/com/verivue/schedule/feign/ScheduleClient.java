package com.verivue.schedule.feign;

import com.verivue.api.schedule.IScheduleClient;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.schedule.dto.Task;
import com.verivue.schedule.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScheduleClient implements IScheduleClient {

    @Autowired
    private TaskService taskService;

    /**
     * Add task
     *
     * @param task
     * @return
     */
    @PostMapping("/api/v1/task/add")
    @Override
    public ResponseResult addTask(@RequestBody  Task task) {
        return ResponseResult.okResult(taskService.addTask(task));
    }

    /**
     * Cancel Task
     *
     * @param taskId
     * @return
     */
    @GetMapping("/api/v1/task/cancel/{taskId}")
    @Override
    public ResponseResult cancelTask(@PathVariable("taskId") long taskId) {
        return ResponseResult.okResult(taskService.cancelTask(taskId));
    }

    /**
     * Pull tasks by type and priority
     *
     * @param type
     * @param priority
     * @return
     */
    @GetMapping("/api/v1/task/poll/{type}/{priority}")
    @Override
    public ResponseResult poll(@PathVariable("type") int type, @PathVariable("priority") int priority) {
        return ResponseResult.okResult(taskService.pollTask(type, priority));
    }
}
