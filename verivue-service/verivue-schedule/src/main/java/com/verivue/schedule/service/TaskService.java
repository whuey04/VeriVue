package com.verivue.schedule.service;

import com.verivue.model.schedule.dto.Task;

public interface TaskService{

    /**
     * Add Task
     * @param task
     * @return
     */
    long addTask(Task task);

    /**
     * Cancel Task
     * @param taskId
     * @return
     */
    boolean cancelTask(long taskId);

    /**
     * Pull tasks by type and priority
     * @param type
     * @param priority
     * @return
     */
    Task pollTask(int type,int priority);
}
