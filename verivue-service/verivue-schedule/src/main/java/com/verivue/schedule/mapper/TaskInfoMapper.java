package com.verivue.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verivue.model.schedule.pojo.TaskInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {

    List<TaskInfo> queryFutureTime(@Param("taskType")int type, @Param("priority")int priority, @Param("future") Date future);
}
