package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.common.constant.ColumConstants;
import com.rainy.sys.service.BaseService;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public T getOne(String column, Object value) {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq(column, value);
        // 防止出现多个记录
//        List<T> list = this.baseMapper.selectList(qw);
//        return list.isEmpty() ? null : list.get(0);
        return this.baseMapper.selectOne(qw);
    }

    @Override
    public boolean exists(String column, Object value) {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

    @Override
    public boolean exists(Integer id, String column, Object value) {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq(column, value);
        qw.ne(ColumConstants.ID, id);
        return this.baseMapper.exists(qw);
    }

}
