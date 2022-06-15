package com.rainy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.sys.entity.Config;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface ConfigService extends IService<Config> {

    String get(String code);
    int getAsInt(String code);
    boolean getAsBoolean(String code);
    boolean update(Config config);

    boolean exists(String column, String value);
    boolean exists(Integer id, String column, String value);

}