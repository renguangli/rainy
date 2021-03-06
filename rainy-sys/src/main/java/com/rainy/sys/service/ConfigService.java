package com.rainy.sys.service;

import com.rainy.sys.entity.Config;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface ConfigService extends BaseService<Config> {

    String get(String code);
    int getAsInt(String code);
    boolean getAsBoolean(String code);
    boolean update(Config config);

}
