package com.rainy.core.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.common.ResultCode;
import com.rainy.common.UserConstants;
import com.rainy.common.exception.BizException;
import com.rainy.core.entity.User;
import com.rainy.core.entity.UserRoleRel;
import com.rainy.core.mapper.UserMapper;
import com.rainy.core.mapper.UserRoleRelMapper;
import com.rainy.core.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/29 17:51
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleRelMapper userRoleRelMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(User user) {
        // 校验邮箱是否被注册
        this.checkExists("email", user.getEmail(), "邮箱[" + user.getEmail() + "]已被注册！");
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
    public void checkExists(String column, String value, String message){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq(column, value);
        User one = getOne(qw);
        if (one != null) {
            throw new BizException(ResultCode.BAD_REQUEST.getCode(), message);
        }
    }
}
