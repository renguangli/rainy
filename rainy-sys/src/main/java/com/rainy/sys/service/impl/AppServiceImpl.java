package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.App;
import com.rainy.sys.mapper.AppMapper;
import com.rainy.sys.service.AppService;
import org.springframework.stereotype.Service;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/4 9:12 PM
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Override
    public boolean exists(String column, String value) {
        QueryWrapper<App> qw = new QueryWrapper<>();
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

    @Override
    public boolean exists(Integer id, String column, String value) {
        QueryWrapper<App> qw = new QueryWrapper<>();
        qw.ne("id", id);
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

}
