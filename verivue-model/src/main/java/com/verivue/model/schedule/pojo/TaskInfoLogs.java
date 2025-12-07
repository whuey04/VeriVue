package com.verivue.model.schedule.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("taskinfo_logs")
public class TaskInfoLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long taskId;

    @TableField("execute_time")
    private Date executeTime;

    @TableField("parameters")
    private byte[] parameters;

    @TableField("priority")
    private Integer priority;

    @TableField("task_type")
    private Integer taskType;

    @Version
    private Integer version;

    /**
     * Status 0=int 1=EXECUTED 2=CANCELLED
     */
    @TableField("status")
    private Integer status;


}