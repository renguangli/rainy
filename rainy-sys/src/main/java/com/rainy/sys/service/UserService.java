package com.rainy.sys.service;

import com.rainy.sys.entity.User;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/29 17:50
 */
public interface UserService extends BaseService<User> {

    User register(User user);

}
