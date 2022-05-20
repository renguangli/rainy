package com.rainy.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainy.task.entity.TaskLog;
import org.apache.ibatis.annotations.Update;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 15:29
 */
public interface TaskLogMapper extends BaseMapper<TaskLog> {

    @Update("truncate table t_task_log")
    boolean clear();

}
