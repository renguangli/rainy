package com.rainy.task.aop;

import com.rainy.common.util.ThrowableUtils;
import com.rainy.task.entity.TaskLog;
import com.rainy.task.service.TaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 记录定时任务执行日志
 *
 * @author renguangli
 * @date 2022/3/14 16:36
 */
@Aspect
@Component
@Slf4j
public class TaskLogAspect {

    @Resource
    private AsyncTaskExecutor asyncTaskExecutor;
    @Resource
    private TaskLogService taskLogService;

    /**
     * 定时任务执行日志
     */
    @Around("execution(public * com.rainy.task.task..*.*(..))")
    public Object saveTaskLog(ProceedingJoinPoint pjp) {
        TaskLog taskLog = buildTaskLog(pjp);
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = pjp.proceed(pjp.getArgs());
            taskLog.setSuccess(true);
            taskLog.setProcessTime(System.currentTimeMillis() - start);
        } catch (Throwable e) {
            log.error("定时任务[{}]执行失败：{}", taskLog.getName(), e.getMessage(), e);
            taskLog.setSuccess(false);
            taskLog.setErrorMessage(ThrowableUtils.toString(e));
        }
        asyncTaskExecutor.execute(() -> taskLogService.save(taskLog));
        return result;
    }

    private TaskLog buildTaskLog(ProceedingJoinPoint pjp) {
        String className = pjp.getTarget().getClass().getName();
        String method = pjp.getSignature().getName();
        JobExecutionContext jobCtx = (JobExecutionContext) pjp.getArgs()[0];
        Date nextFireTime = jobCtx.getTrigger().getNextFireTime();
        JobKey jobKey = jobCtx.getJobDetail().getKey();
        TaskLog taskLog = new TaskLog();
        taskLog.setClassName(className);
        taskLog.setMethod(method);
        taskLog.setName(jobKey.getName());
        taskLog.setGroup(jobKey.getGroup());
        taskLog.setDatetime(LocalDateTime.now());
        taskLog.setNextDatetime(nextFireTime);
        return taskLog;
    }

}
