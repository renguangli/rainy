package com.rainy.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.core.entity.Position;
import com.rainy.core.mapper.PositionMapper;
import com.rainy.core.service.PositionService;
import org.springframework.stereotype.Service;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/25 10:17
 */
@Service
public class PositionServiceImpl
        extends ServiceImpl<PositionMapper, Position> implements PositionService {


}
