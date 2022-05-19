package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.OperationLog;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface OperationLogService extends IService<OperationLog> {

    void clear();

}
