package com.rainy.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainy.core.entity.LoginLog;
import org.apache.ibatis.annotations.Update;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:55
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    @Update("truncate table t_login_log")
    void clear();
}
