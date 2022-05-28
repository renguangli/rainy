package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.UserRoleRel;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/25 10:28
 */
public interface UserRoleRelService extends IService<UserRoleRel> {

    public boolean assignRoles(Integer userId, List<Integer> roleIds);
}
