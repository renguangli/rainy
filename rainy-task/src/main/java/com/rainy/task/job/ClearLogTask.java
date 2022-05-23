package com.rainy.task.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.core.entity.LoginLog;
import com.rainy.core.entity.OperationLog;
import com.rainy.core.service.ConfigService;
import com.rainy.core.service.LoginLogService;
import com.rainy.core.service.OperationLogService;
import org.quartz.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 清理登录、操作日志定时任务
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
    private LoginLogService loginLogService;
    @Resource
    private OperationLogService operationLogService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int logRetentionDays = configService.getAsInt(ConfigConstants.LOG_RETENTION_DAYS);
        if (logRetentionDays > 0) {
            // 1.删除登录日志
            QueryWrapper<LoginLog> qw = new QueryWrapper<>();
            qw.lt("datetime", LocalDateTime.now().minusDays(logRetentionDays));
            loginLogService.remove(qw);
            // 2.删除操作日志
            QueryWrapper<OperationLog> opqw = new QueryWrapper<>();
            opqw.lt("datetime", LocalDateTime.now().minusDays(logRetentionDays));
            operationLogService.remove(opqw);
        }
    }
}
