package com.rainy.task.service.impl;

import com.rainy.common.exception.NotExistsException;
import com.rainy.task.entity.Task;
import com.rainy.task.service.JobService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 10:24
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final Scheduler scheduler;

    /**
     *
     * 启动任务，如果已启动什么都不做
     * @param task Task
     * @throws SchedulerException SchedulerException
     */
    @Override
    public void addJob(Task task) throws SchedulerException {
        try {
            Class<Job> clazz = (Class<Job>) Class.forName(task.getClassName());
            addJob(clazz, task);
        } catch (ClassNotFoundException e) {
            throw new NotExistsException("Class[" + task.getClassName() + "] not exists");
        }
    }


    /**
     * 启动任务，如果已启动什么都不做
     * @param clazz clazz
     * @param task task
     * @throws SchedulerException SchedulerException
     */
    @Override
    public void addJob(Class<? extends Job> clazz, Task task) throws SchedulerException {
        JobKey jobKey = new JobKey(task.getName(), task.getGroup());
        // 构建job信息
        JobDetail jobDetail = buildJobDetail(clazz, jobKey);
        // 按照 cron 表达式构建
        CronTrigger trigger = buildCronTrigger(task);
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void deleteJob(Task task) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(task.getName(), task.getGroup());
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(JobKey.jobKey(task.getName(), task.getGroup()));
    }

    @Override
    public void updateJob(Task task) throws SchedulerException {
        this.deleteJob(task);
        this.addJob(task);
        if (task.getStatus() == 1) {
            pauseJob(task);
        }
    }

    @Override
    public void pauseJob(Task task) throws SchedulerException {
        // 暂停 job 执行
        JobKey jobKey = JobKey.jobKey(task.getName(), task.getGroup());
        scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeJob(Task task) throws SchedulerException {
        // 恢复 job 调度
        JobKey jobKey = JobKey.jobKey(task.getName(), task.getGroup());
        scheduler.resumeJob(jobKey);
    }

    private JobDetail buildJobDetail(Class<? extends Job> jobClass, JobKey jobKey) {
        return JobBuilder.newJob()
                .ofType(jobClass)
                .withIdentity(jobKey)
                .requestRecovery() //  执行过程中出现意外 比如服务器down了 那么在重启时候是否恢复任务
                .build();
    }

    private CronTrigger buildCronTrigger(Task task) {
        /*
         * CronTrigger
         * withMisfireHandlingInstructionDoNothing
         * 不触发立即执行
         * 等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
         * withMisfireHandlingInstructionIgnoreMisfires
         * 以错过的第一个频率时间立刻开始执行
         * 重做错过的所有频率周期后
         * 当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
         * withMisfireHandlingInstructionFireAndProceed
         * 以当前时间为触发频率立刻触发一次执行
         * 然后按照Cron频率依次执行
         */
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                .cronSchedule(task.getCron())
                .withMisfireHandlingInstructionDoNothing();
        // 按新的 cron表达式构建 crontrigger
        return TriggerBuilder.newTrigger()
                .withIdentity(task.getName(), task.getGroup())
                .withSchedule(cronScheduleBuilder)
                .startNow()
                .build(); // 延迟 1 s执行
    }

}
