package com.rainy.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.core.entity.Position;
import com.rainy.core.mapper.PositionMapper;
import com.rainy.core.service.PositionService;
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
        Position position = this.getOne(qw);
        return position != null;
    }
}
