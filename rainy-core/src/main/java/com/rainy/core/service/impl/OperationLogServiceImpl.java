package com.rainy.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.core.entity.OperationLog;
import com.rainy.core.mapper.OperationLogMapper;
import com.rainy.core.service.OperationLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * spring-boot-example-console
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
@Service
public class OperationLogServiceImpl
        extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Async
    @Override
    public void asyncSave(OperationLog operationLog) {
        this.save(operationLog);
    }

    @Override
    public void clear() {
        this.baseMapper.clear();
    }
}
