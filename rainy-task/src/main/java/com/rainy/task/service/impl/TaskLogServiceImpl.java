package com.rainy.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.task.entity.TaskLog;
import com.rainy.task.mapper.TaskLogMapper;
import com.rainy.task.service.TaskLogService;
import org.springframework.stereotype.Service;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/28 15:30
 */
@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements TaskLogService {

    @Override
    public boolean clear() {
        return this.baseMapper.clear();
    }
}
