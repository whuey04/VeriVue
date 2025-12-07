package com.verivue.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.verivue.common.constants.ScheduleConstants;
import com.verivue.common.redis.CacheService;
import com.verivue.model.schedule.dto.Task;
import com.verivue.model.schedule.pojo.TaskInfo;
import com.verivue.model.schedule.pojo.TaskInfoLogs;
import com.verivue.schedule.mapper.TaskInfoLogsMapper;
import com.verivue.schedule.mapper.TaskInfoMapper;
import com.verivue.schedule.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private TaskInfoMapper taskInfoMapper;
    @Autowired
    private TaskInfoLogsMapper taskInfoLogsMapper;

    /**
     * Add Task
     *
     * @param task
     * @return
     */
    @Override
    public long addTask(Task task) {
        // add task to database
        boolean success = addTaskToDatabase(task);

        if (success) {
            // if success, add task to redis
            addTaskToCache(task);
        }

        return task.getTaskId();
    }

    /**
     * Cancel Task
     *
     * @param taskId
     * @return
     */
    @Override
    public boolean cancelTask(long taskId) {
        boolean flag = false;

        // delete task and update log
        Task task = updateToDatabase(taskId,ScheduleConstants.CANCELLED);

        // delete data in redis
        if (task != null) {
            deleteTaskFromRedis(task);
            flag = true;
        }
        return flag;
    }

    /**
     * Pull tasks by type and priority
     *
     * @param type
     * @param priority
     * @return
     */
    @Override
    public Task pollTask(int type, int priority) {
        Task task = null;
        try {
            String key = type+"_"+priority;
            String task_json = cacheService.lRightPop(ScheduleConstants.TOPIC + key);
            if(StringUtils.isNotBlank(task_json)){
                task = JSON.parseObject(task_json, Task.class);

                updateToDatabase(task.getTaskId(),ScheduleConstants.EXECUTED);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("poll task exception");
        }

        return task;
    }

    /**
     * Scheduled refresh for future data
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void refresh(){

        String token = cacheService.tryLock("FUTURE_TASK_SYNC", 1000 * 30);
        if(StringUtils.isNotBlank(token)){
            log.info("Scheduled task for refreshing future data");

            Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");
            for (String futureKey : futureKeys) {//future_100_50

                String topicKey = ScheduleConstants.TOPIC+futureKey.split(ScheduleConstants.FUTURE)[1];

                Set<String> tasks = cacheService.zRangeByScore(futureKey, 0, System.currentTimeMillis());

                if(!tasks.isEmpty()){
                    cacheService.refreshWithPipeline(futureKey,topicKey,tasks);
                    log.info("Successfully refreshed " + futureKey + " to " + topicKey);
                }
            }
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    @PostConstruct
    public void reloadData() {
        clearCache();
        log.info("Successfully synchronized data from database to cache");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);

        List<TaskInfo> allTasks = taskInfoMapper.selectList(Wrappers.<TaskInfo>lambdaQuery().lt(TaskInfo::getExecuteTime,calendar.getTime()));
        if(allTasks != null && allTasks.size() > 0){
            for (TaskInfo taskInfo : allTasks) {
                Task task = new Task();
                BeanUtils.copyProperties(taskInfo,task);
                task.setExecuteTime(taskInfo.getExecuteTime().getTime());
                addTaskToCache(task);
            }
        }
    }

    private void clearCache(){
        Set<String> futurekeys = cacheService.scan(ScheduleConstants.FUTURE + "*");// future_
        Set<String> topickeys = cacheService.scan(ScheduleConstants.TOPIC + "*");// topic_
        cacheService.delete(futurekeys);
        cacheService.delete(topickeys);
    }


    /**
     * Delete Task and Update the Task Logs
     * @param taskId
     * @param status
     * @return
     */
    private Task updateToDatabase(long taskId, int status) {
        Task task = null;
        try {
            //Delete task
            taskInfoMapper.deleteById(taskId);

            TaskInfoLogs logs = taskInfoLogsMapper.selectById(taskId);
            logs.setStatus(status);
            taskInfoLogsMapper.updateById(logs);

            task = new Task();
            BeanUtils.copyProperties(logs, task);
            task.setExecuteTime(logs.getExecuteTime().getTime());

        }catch (Exception e) {
            log.error("task cancel exception taskid={}",taskId);
        }
        return task;
    }

    /**
     * Delete task from redis
     * @param task
     */
    private void deleteTaskFromRedis(Task task) {
        String key = task.getTaskType() + "_" + task.getPriority();

        if(task.getExecuteTime()<=System.currentTimeMillis()){
            cacheService.lRemove(ScheduleConstants.TOPIC + key,0,JSON.toJSONString(task));
        }else {
            cacheService.zRemove(ScheduleConstants.FUTURE + key, JSON.toJSONString(task));
        }
    }

    /**
     * Add Task to database
     * @param task
     * @return
     */
    private boolean addTaskToDatabase(Task task) {
        boolean flag = false;

        try {
            TaskInfo taskInfo = new TaskInfo();
            BeanUtils.copyProperties(task, taskInfo);
            taskInfo.setExecuteTime(new Date(task.getExecuteTime()));
            // Save in taskinfo table
            taskInfoMapper.insert(taskInfo);

            task.setTaskId(taskInfo.getTaskId());

            TaskInfoLogs taskInfoLogs = new TaskInfoLogs();
            BeanUtils.copyProperties(taskInfo, taskInfoLogs);
            taskInfoLogs.setVersion(1);
            taskInfoLogs.setStatus(ScheduleConstants.SCHEDULED);
            // Save in taskinfoLogs table
            taskInfoLogsMapper.insert(taskInfoLogs);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * Add task to redis
     * @param task
     */
    private void addTaskToCache(Task task) {
        String key = task.getTaskType() + "_" + task.getPriority();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        long nextScheduleTime = calendar.getTimeInMillis();

        if(task.getExecuteTime() <= System.currentTimeMillis()) {
            cacheService.lLeftPush(ScheduleConstants.TOPIC + key, JSON.toJSONString(task));
        }
        else if (task.getExecuteTime() <= nextScheduleTime) {
            cacheService.zAdd(ScheduleConstants.FUTURE + key, JSON.toJSONString(task), task.getExecuteTime());
        }
    }
}
