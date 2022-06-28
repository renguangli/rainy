package com.rainy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.sys.entity.Role;
import com.rainy.sys.entity.RoleMenuRel;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/14 13:32
 */
public interface RoleService extends IService<Role> {

    boolean deleteById(Integer id);

    boolean assignMenus(Integer roleId, List<RoleMenuRel> roleMenuRelList);

    boolean isDefault(Integer roleId);

}
