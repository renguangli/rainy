package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.LoginLog;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface LoginLogService extends IService<LoginLog> {

    void clear();

}
