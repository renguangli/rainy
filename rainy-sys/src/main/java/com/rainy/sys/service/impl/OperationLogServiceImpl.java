package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.OperationLog;
import com.rainy.sys.mapper.OperationLogMapper;
import com.rainy.sys.service.OperationLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * rainy-console
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
