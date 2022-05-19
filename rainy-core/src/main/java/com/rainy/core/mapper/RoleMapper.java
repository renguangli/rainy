package com.rainy.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainy.core.entity.Role;

import java.util.List;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/11 17:37
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色列表
     *
     * @param userId the user id
     * @return the list
     */
    List<Role> listRolesByUserId(Object userId);

}
