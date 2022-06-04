package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.Role;
import com.rainy.sys.entity.RoleMenuRel;
import com.rainy.sys.entity.UserRoleRel;
import com.rainy.sys.mapper.RoleMapper;
import com.rainy.sys.service.RoleMenuRelService;
import com.rainy.sys.service.RoleService;
import com.rainy.sys.service.UserRoleRelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/14 13:33
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl
        extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuRelService roleMenuRelService;
    private final UserRoleRelService userRoleRelService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer id) {
        // 1. 删除用户角色关联数据
        QueryWrapper<UserRoleRel> userRoleRelQW = new QueryWrapper<>();
        userRoleRelQW.eq("role_id", id);
        userRoleRelService.remove(userRoleRelQW);
        // 2. 删除角色菜单关联数据
        QueryWrapper<RoleMenuRel> qw = new QueryWrapper<>();
        qw.eq("role_id", id);
        roleMenuRelService.remove(qw);
        // 3.删除角色
        return this.baseMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignMenus(Integer roleId, List<RoleMenuRel> roleMenuRelList) {
        QueryWrapper<RoleMenuRel> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        roleMenuRelService.remove(qw);
        return roleMenuRelService.saveBatch(roleMenuRelList);
    }

    @Override
    public boolean isDefault(Integer roleId) {
        Role role = this.getById(roleId);
        return role.getDefaultd();
    }

    @Override
    public boolean exists(String column, String value) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

    @Override
    public boolean exists(Integer id, String column, String value) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.ne("id", id);
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

}
