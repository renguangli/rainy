package com.rainy.task.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.ConfigConstants;
import com.rainy.core.entity.OperationLog;
import com.rainy.core.service.ConfigService;
import com.rainy.core.service.OperationLogService;
import org.quartz.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 定时清理日志
 *
 * @author renguangli
 * @date 2022/4/9 10:12
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ClearLogTask implements Job {

    @Resource
    private ConfigService configService;
    @Resource
    private OperationLogService operationLogService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int logRetentionDays = configService.getAsInt(ConfigConstants.LOG_RETENTION_DAYS);
        if (logRetentionDays > 0) {
            QueryWrapper<OperationLog> qw = new QueryWrapper<>();
            qw.lt("datetime", LocalDateTime.now().minusDays(logRetentionDays));
            operationLogService.remove(qw);
        }
    }
}
