package com.rainy.sys.service.impl;

import com.rainy.common.enums.TaskStatus;
import com.rainy.common.exception.NotExistsException;
import com.rainy.sys.entity.Task;
import com.rainy.sys.mapper.TaskMapper;
import com.rainy.sys.service.JobService;
import com.rainy.sys.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 10:24
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl extends BaseServiceImpl<TaskMapper, Task> implements TaskService {

    private final JobService jobService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTask(Task task) throws SchedulerException {
        boolean result = this.save(task);
        jobService.addJob(task);
        if (TaskStatus.PAUSED.getCode() == task.getStatus()) {
            jobService.pauseJob(task);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeTaskById(Integer id) throws SchedulerException {
        Task task = this.getById(id);
        jobService.deleteJob(task);
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchRemoveTaskById(List<Integer> ids) throws SchedulerException {
        for (Integer id : ids) {
            this.removeTaskById(id);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTaskById(Task task) throws SchedulerException {
        this.getById(task.getId()); // 当前任务是否存在
        // 更新数据库
        boolean result = this.updateById(task);
        // 更新 quartz 任务
        jobService.updateJob(task);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pauseTask(Integer id) throws SchedulerException {
        Task task = this.getById(id);
        task.setStatus(TaskStatus.PAUSED.getCode());
        this.updateById(task);
        jobService.pauseJob(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeTask(Integer id) throws SchedulerException {
        Task task = this.getById(id);
        task.setStatus(TaskStatus.STARTUP.getCode());
        this.updateById(task);
        jobService.resumeJob(task);
    }

    @Override
    public Task getById(Serializable id) {
        Task task = super.getById(id);
        if (task == null) {
            throw new NotExistsException("Task[id:" + id + "] not exists.");
        }
        return task;
    }

}
