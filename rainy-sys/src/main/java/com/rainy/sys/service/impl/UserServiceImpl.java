package com.rainy.sys.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.common.constant.UserConstants;
import com.rainy.common.enums.DefaultRole;
import com.rainy.sys.entity.Role;
import com.rainy.sys.entity.User;
import com.rainy.sys.entity.UserRoleRel;
import com.rainy.sys.mapper.RoleMapper;
import com.rainy.sys.mapper.UserMapper;
import com.rainy.sys.mapper.UserRoleRelMapper;
import com.rainy.sys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/29 17:51
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleRelMapper userRoleRelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(User user) {
        // 给用户随机一个名字
        user.setName(UUID.fastUUID().toString(true).substring(8));
        user.setOrgId(1); // 给用户一个默认组织
        user.setStatus(UserConstants.STATUS_NOT_ACTIVATE); // 注册用户默认未激活状态
        userMapper.insert(user);

        // // 给用户一个默认角色
        String roleCode = DefaultRole.DEFAULT.getName();
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq("code", roleCode);
        Role role = roleMapper.selectOne(qw);

        UserRoleRel userRoleRel = new UserRoleRel(user.getId(), role.getId());
        userRoleRelMapper.insert(userRoleRel);
        return user;
    }

    @Override
    public boolean exists(String column, String value){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq(column, value);
        return getOne(qw) != null;
    }
}
