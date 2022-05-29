package com.rainy.core.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.common.enums.UserConstants;
import com.rainy.core.entity.User;
import com.rainy.core.entity.UserRoleRel;
import com.rainy.core.mapper.UserMapper;
import com.rainy.core.mapper.UserRoleRelMapper;
import com.rainy.core.service.UserService;
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

    private final UserRoleRelMapper userRoleRelMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(User user) {
        // 给用户随机一个名字
        user.setName(UUID.fastUUID().toString(true).substring(8));
        user.setOrgId(1); // 给用户一个默认组织
        user.setStatus(UserConstants.STATUS_NOT_ACTIVATE); // 注册用户默认未激活状态
        userMapper.insert(user);

        // // 给用户一个默认角色
        UserRoleRel userRoleRel = new UserRoleRel(user.getId(), 1);
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
