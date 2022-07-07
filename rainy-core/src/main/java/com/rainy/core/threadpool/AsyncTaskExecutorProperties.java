package com.rainy.core.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/20 10:01
 */
@Data
@Component
@ConfigurationProperties(prefix = "async.task.executor")
public class AsyncTaskExecutorProperties {

    private static final String THREAD_POOL_PREFIX = "async_task_executor_";
    private static final int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private int coreSize = DEFAULT_POOL_SIZE;
    private int maxSize = DEFAULT_POOL_SIZE;
    private int queueCapacity = 100;
    private String threadNamePrefix = THREAD_POOL_PREFIX;
}
