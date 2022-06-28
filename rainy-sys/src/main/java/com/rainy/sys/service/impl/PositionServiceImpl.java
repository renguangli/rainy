package com.rainy.sys.service.impl;

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

}
