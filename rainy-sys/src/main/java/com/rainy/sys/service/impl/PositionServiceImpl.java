package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.Position;
import com.rainy.sys.mapper.PositionMapper;
import com.rainy.sys.service.PositionService;
import org.springframework.stereotype.Service;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/25 10:17
 */
@Service
public class PositionServiceImpl
        extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Override
    public boolean exists(String column, String value) {
        QueryWrapper<Position> qw = new QueryWrapper<>();
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

    @Override
    public boolean exists(Integer id, String column, String value) {
        QueryWrapper<Position> qw = new QueryWrapper<>();
        qw.ne("id", id);
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }
}
