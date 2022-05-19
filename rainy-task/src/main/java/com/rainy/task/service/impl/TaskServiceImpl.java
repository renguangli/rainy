package com.rainy.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.common.exception.NotExistsException;
import com.rainy.task.entity.Task;
import com.rainy.task.mapper.TaskMapper;
import com.rainy.task.service.JobService;
import com.rainy.task.service.TaskService;
import com.rainy.task.task.TaskStatus;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/28 10:24
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Resource
    private JobService jobService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTask(Task task) throws SchedulerException {
        boolean result = this.save(task);
        jobService.addJob(task);
        if (TaskStatus.PAUSED == task.getStatus()) {
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
        task.setStatus(TaskStatus.PAUSED);
        this.updateById(task);
        jobService.pauseJob(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeTask(Integer id) throws SchedulerException {
        Task task = this.getById(id);
        task.setStatus(TaskStatus.STARTUP);
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
