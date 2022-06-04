package com.rainy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.sys.entity.Position;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/25 10:17
 */
public interface PositionService extends IService<Position> {

    boolean exists(String column, String value);
    boolean exists(Integer id, String column, String value);
}
