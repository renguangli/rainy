package com.rainy.task.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/28 10:12
 */
@Component
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
        try {
            Thread.sleep(1111);
            System.out.println("hello task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
