package com.rainy.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 *
 * @author renguangli
 * @date 2022/5/19 09:51
 */
@Slf4j
@Configuration
public class ThreadPoolConfiguration {

    private static final String THREAD_POOL_PREFIX = "async_task_executor";

    @Bean
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        int coreSize = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 固定线程数
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(coreSize);
        // 线程名前缀
        executor.setThreadNamePrefix(THREAD_POOL_PREFIX);
        // 任务队列最大任务数
        executor.setQueueCapacity(100);
        // 任务拒绝测试，抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

}
