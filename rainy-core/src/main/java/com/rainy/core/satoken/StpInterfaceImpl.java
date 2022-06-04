package com.rainy.core.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.rainy.common.enums.DefaultRole;
import com.rainy.sys.entity.Role;
import com.rainy.sys.mapper.MenuMapper;
import com.rainy.sys.mapper.RoleMapper;
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
        List<String> roleCodes = map2RoleCodes(roles);
        // 如果是超级管理员返回所有接口权限
        if (roleCodes.contains(DefaultRole.SUPPER_ADMIN.getName())) {
            return menuMapper.selectMenusByRoleIds(null);
        }
        return menuMapper.selectMenusByRoleIds(map2RoleIds(roles));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<Role> roles = roleMapper.listRolesByUserId(loginId);
        return roles.stream().map(Role::getCode).collect(Collectors.toList());
    }

    private List<String> map2RoleCodes(List<Role> roles) {
        return roles.stream()
                .map(Role::getCode)
                .collect(Collectors.toList());
    }

    private List<Integer> map2RoleIds(List<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }
}
