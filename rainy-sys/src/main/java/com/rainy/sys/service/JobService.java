package com.rainy.sys.service;

import com.rainy.sys.entity.Task;
import org.quartz.Job;
import org.quartz.SchedulerException;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 10:23
 */
public interface JobService {

    void addJob(Task task) throws SchedulerException;
    void addJob(Class<? extends Job> clazz, Task task) throws SchedulerException;

    void deleteJob(Task task) throws SchedulerException;

    void updateJob(Task task) throws SchedulerException;

    void pauseJob(Task task) throws SchedulerException;

    void resumeJob(Task task) throws SchedulerException;

}
