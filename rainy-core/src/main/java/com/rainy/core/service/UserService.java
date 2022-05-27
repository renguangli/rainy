package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.User;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/29 17:50
 */
public interface UserService extends IService<User> {

    User register(User user);

    boolean exists(String column, String value);

}
