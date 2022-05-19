package com.rainy.admin.config.security;

import cn.dev33.satoken.stp.StpInterface;
import com.rainy.core.entity.Role;
import com.rainy.core.mapper.MenuMapper;
import com.rainy.core.mapper.RoleMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 *
 * @author renguangli
 * @date 2022 /3/11 17:03
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> roleList = getRoleList(loginId, loginType);
        if (roleList.isEmpty()) {
            return Collections.emptyList();
        }
        List<Role> roles = roleMapper.listRolesByUserId(loginId);
        List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        return menuMapper.selectMenusByRoleIds(roleIds);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<Role> roles = roleMapper.listRolesByUserId(loginId);
        return roles.stream().map(Role::getCode).collect(Collectors.toList());
    }

}
