package com.rainy.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.core.entity.Config;
import com.rainy.core.mapper.ConfigMapper;
import com.rainy.core.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "config")
public class ConfigServiceImpl
        extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    @Cacheable(key = "#code")
    public String get(String code) {
        QueryWrapper<Config> qw = new QueryWrapper<>();
        qw.select("value").eq("code", code);
        Config config = this.baseMapper.selectOne(qw);
        return config == null ? null : config.getValue().toString();
    }

    @Override
    @Cacheable(key = "#code") // aop 方法自调用不会生效
    public int getAsInt(String code) {
        return Integer.parseInt(get(code));
    }

    @Override
    @Cacheable(key = "#code") // aop 方法自调用不会生效
    public boolean getAsBoolean(String code) {
        return Boolean.parseBoolean(get(code));
    }

    @Override
    @CacheEvict(key = "#config.code")
    public boolean update(Config config) {
        return this.baseMapper.updateById(config) > 0;
    }

    @Override
    public boolean exists(String column, String value) {
        QueryWrapper<Config> qw = new QueryWrapper<>();
        qw.eq(column, value);
        Config config = this.baseMapper.selectOne(qw);
        return config != null;
    }

}
