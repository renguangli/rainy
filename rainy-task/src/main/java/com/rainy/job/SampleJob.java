package com.rainy.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 测试任务
 *
 * @author renguangli
 * @date 2022/3/28 10:12
 */
@Slf4j
@Component
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
        try {
            log.debug("-------- 测试任务 --------");
            Thread.sleep(1111);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
