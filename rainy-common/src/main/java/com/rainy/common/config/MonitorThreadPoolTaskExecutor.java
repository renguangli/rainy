package com.rainy.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 打印线程池状态
 *
 * @author renguangli
 * @date 2022/5/20 10:14
 */
@Slf4j
public class MonitorThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        printThreadPoolInfo();
        super.execute(task);
    }
    @Override
    public Future<?> submit(Runnable task) {
        printThreadPoolInfo();
        return super.submit(task);
    }

    private void printThreadPoolInfo(){
        ThreadPoolExecutor executor = getThreadPoolExecutor();
        log.info("{},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(),
                executor.getTaskCount(),
                executor.getCompletedTaskCount(),
                executor.getActiveCount(),
                executor.getQueue().size());
    }

}
