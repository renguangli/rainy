package com.rainy.core.aop;

import com.rainy.common.util.ThrowableUtils;
import com.rainy.sys.entity.TaskLog;
import com.rainy.sys.service.TaskLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

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
@RequiredArgsConstructor
public class TaskLogAspect {

    private final TaskLogService taskLogService;

    /**
     * 定时任务执行日志
     */
    @Around("execution(public * com.rainy.job..*.*(..))")
    public Object saveTaskLog(ProceedingJoinPoint pjp) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        TaskLog taskLog = buildTaskLog(pjp);
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = pjp.proceed(pjp.getArgs());
            taskLog.setSuccess(true);
            stopWatch.stop();
            taskLog.setProcessTime(stopWatch.getTotalTimeMillis());
        } catch (Throwable e) {
            log.error("定时任务[{}]执行失败：{}", taskLog.getName(), e.getMessage(), e);
            taskLog.setSuccess(false);
            taskLog.setErrorMessage(ThrowableUtils.toString(e));
        }
        taskLogService.asyncSave(taskLog);
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
