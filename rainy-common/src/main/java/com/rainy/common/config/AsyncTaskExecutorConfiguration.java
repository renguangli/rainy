package com.rainy.common.config;

import cn.hutool.log.LogFactory;
import com.rainy.common.MonitorThreadPoolTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 *
 * @author renguangli
 * @date 2022/5/19 09:51
 */
@EnableAsync
@Configuration
public class AsyncTaskExecutorConfiguration {

    @Resource
    private AsyncTaskExecutorProperties asyncTaskExecutorProperties;

    @Bean
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new MonitorThreadPoolTaskExecutor();
        // 固定线程数
        executor.setCorePoolSize(asyncTaskExecutorProperties.getCoreSize());
        executor.setMaxPoolSize(asyncTaskExecutorProperties.getMaxSize());
        // 线程名前缀
        executor.setThreadNamePrefix(asyncTaskExecutorProperties.getThreadNamePrefix());
        // 任务队列最大任务数
        executor.setQueueCapacity(asyncTaskExecutorProperties.getQueueCapacity());
        // 任务拒绝策略，抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

}
