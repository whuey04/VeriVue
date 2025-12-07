package com.verivue.schedule.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.verivue.model.schedule.dto.Task;
import com.verivue.schedule.ScheduleApplication;
import com.verivue.schedule.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@SpringBootTest(classes = ScheduleApplication.class)
@RunWith(SpringRunner.class)
public class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void addTask() {
        Task task = new Task();
        task.setTaskType(100);
        task.setPriority(50);
        task.setParameters("task test".getBytes());
        task.setExecuteTime(new Date().getTime()+505);

        long task1 = taskService.addTask(task);
        System.out.println(task1);
    }

    @Test
    public void cancelTask() {
        taskService.cancelTask(1956277648812924930L);
    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testZSetAddRemove() {
        String key = "future_100_50";

        // 模拟一个任务对象
        TaskDebug task = new TaskDebug();
        task.setTaskId(1956264957788520449L);
        task.setTaskType(100);
        task.setPriority(50);
        task.setExecuteTime(System.currentTimeMillis() + 10000);
        task.setParameters("task test".getBytes());

        // 统一 JSON 序列化
        String json = JSON.toJSONString(task, SerializerFeature.SortField);

        // 添加到 ZSet
        stringRedisTemplate.opsForZSet().add(key, json, task.getExecuteTime());
        System.out.println("Added member: " + json);

        // 打印 ZSet 所有成员
        Set<String> members = stringRedisTemplate.opsForZSet().range(key, 0, -1);
        System.out.println("ZSet members:");
        for (String m : members) {
            System.out.println(m);
            System.out.println("bytes: " + Arrays.toString(m.getBytes(StandardCharsets.UTF_8)));
        }

        // 准备删除
        System.out.println("Delete member bytes: " + Arrays.toString(json.getBytes(StandardCharsets.UTF_8)));

        // 删除
        Long removed = stringRedisTemplate.opsForZSet().remove(key, json);
        System.out.println("Removed count: " + removed);

        // 打印删除后成员
        Set<String> afterRemove = stringRedisTemplate.opsForZSet().range(key, 0, -1);
        System.out.println("ZSet after remove:");
        afterRemove.forEach(System.out::println);
    }

    // 最小任务类，用于测试
    public static class TaskDebug {
        private long taskId;
        private int taskType;
        private int priority;
        private long executeTime;
        private byte[] parameters;

        // getter/setter
        public long getTaskId() { return taskId; }
        public void setTaskId(long taskId) { this.taskId = taskId; }
        public int getTaskType() { return taskType; }
        public void setTaskType(int taskType) { this.taskType = taskType; }
        public int getPriority() { return priority; }
        public void setPriority(int priority) { this.priority = priority; }
        public long getExecuteTime() { return executeTime; }
        public void setExecuteTime(long executeTime) { this.executeTime = executeTime; }
        public byte[] getParameters() { return parameters; }
        public void setParameters(byte[] parameters) { this.parameters = parameters; }
    }
}
