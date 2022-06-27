package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.Config;
import com.rainy.sys.mapper.ConfigMapper;
import com.rainy.sys.service.ConfigService;
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
@CacheConfig(cacheNames = "rainy:config")
public class ConfigServiceImpl
        extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    @Cacheable(key = "#code")
    public String get(String code) {
        QueryWrapper<Config> qw = new QueryWrapper<>();
        qw.select("value").eq("code", code);
        Config config = this.baseMapper.selectOne(qw);
        return config == null ? null : config.getValue();
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
        return this.baseMapper.exists(qw);
    }

    @Override
    public boolean exists(Integer id, String column, String value) {
        QueryWrapper<Config> qw = new QueryWrapper<>();
        qw.ne("id", id);
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

}
