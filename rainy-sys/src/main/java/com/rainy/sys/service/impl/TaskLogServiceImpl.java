package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.TaskLog;
import com.rainy.sys.mapper.TaskLogMapper;
import com.rainy.sys.service.TaskLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 15:30
 */
@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements TaskLogService {

    @Async
    @Override
    public void asyncSave(TaskLog taskLog) {
        this.save(taskLog);
    }

    @Override
    public boolean clear() {
        return this.baseMapper.clear();
    }
}
