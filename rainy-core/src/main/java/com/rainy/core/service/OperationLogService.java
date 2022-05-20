package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.OperationLog;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface OperationLogService extends IService<OperationLog> {

    void asyncSave(OperationLog operationLog);
    void clear();

}
