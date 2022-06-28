package com.rainy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseService<T> extends IService<T> {

    T getOne(String column, Object value);

    boolean exists(String column, Object value);

    boolean exists(Integer id, String column, Object value);

}
