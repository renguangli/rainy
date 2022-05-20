package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.Config;

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

}
