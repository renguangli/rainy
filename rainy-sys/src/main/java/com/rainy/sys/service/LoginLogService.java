package com.rainy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.sys.entity.LoginLog;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface LoginLogService extends IService<LoginLog> {

    void asyncSave(LoginLog loginLog);
    void clear();

}
