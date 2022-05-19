package com.rainy.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.task.entity.Task;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/28 10:23
 */
public interface TaskService extends IService<Task> {

    boolean addTask(Task task) throws SchedulerException;
    boolean removeTaskById(Integer id) throws SchedulerException;
    boolean batchRemoveTaskById(List<Integer> ids) throws SchedulerException;
    boolean updateTaskById(Task task) throws SchedulerException;

    void pauseTask(Integer id) throws SchedulerException;
    void resumeTask(Integer id) throws SchedulerException;
}
