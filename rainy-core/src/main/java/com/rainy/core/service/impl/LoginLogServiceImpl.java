package com.rainy.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.core.entity.LoginLog;
import com.rainy.core.entity.OperationLog;
import com.rainy.core.mapper.LoginLogMapper;
import com.rainy.core.service.LoginLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * rainy-console
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
@Service
public class LoginLogServiceImpl
        extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Async
    @Override
    public void asyncSave(LoginLog loginLog) {
        this.save(loginLog);
    }

    @Override
    public void clear() {
        this.baseMapper.clear();
    }

}
