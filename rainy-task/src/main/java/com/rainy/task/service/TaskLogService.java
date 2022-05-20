package com.rainy.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.task.entity.TaskLog;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 15:30
 */
public interface TaskLogService extends IService<TaskLog> {

    void asyncSave(TaskLog taskLog);
    boolean clear();
}
