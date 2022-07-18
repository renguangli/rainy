package com.rainy.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 将调整后的文件回传
 *
 * @author renguangli
 * @date 2022/7/18 17:22
 */
@Component
public class FileTransferJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }

}
