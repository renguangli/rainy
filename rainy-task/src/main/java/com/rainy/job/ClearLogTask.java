package com.rainy.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.sys.entity.LoginLog;
import com.rainy.sys.entity.OperationLog;
import com.rainy.sys.service.ConfigService;
import com.rainy.sys.service.LoginLogService;
import com.rainy.sys.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.time.LocalDateTime;

/**
 * 清理登录、操作日志定时任务
 *
 * @author renguangli
 * @date 2022/4/9 10:12
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class ClearLogTask implements Job {

    private final ConfigService configService;
    private final LoginLogService loginLogService;
    private final OperationLogService operationLogService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int logRetentionDays = configService.getAsInt(ConfigConstants.LOG_RETENTION_DAYS);
        if (logRetentionDays > 0) {
            // 1.删除登录日志
            log.debug("-------- 删除登录日志,日志保留天数：{} --------", logRetentionDays);
            QueryWrapper<LoginLog> qw = new QueryWrapper<>();
            qw.lt("datetime", LocalDateTime.now().minusDays(logRetentionDays));
            loginLogService.remove(qw);
            // 2.删除操作日志
            log.debug("-------- 删除操作日志,日志保留天数：{} --------", logRetentionDays);
            QueryWrapper<OperationLog> opqw = new QueryWrapper<>();
            opqw.lt("datetime", LocalDateTime.now().minusDays(logRetentionDays));
            operationLogService.remove(opqw);
        }
    }
}
