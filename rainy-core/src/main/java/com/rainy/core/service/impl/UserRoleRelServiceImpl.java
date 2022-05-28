package com.rainy.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.core.entity.UserRoleRel;
import com.rainy.core.mapper.UserRoleRelMapper;
import com.rainy.core.service.UserRoleRelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/25 10:28
 */
@Service
public class UserRoleRelServiceImpl
        extends ServiceImpl<UserRoleRelMapper, UserRoleRel> implements UserRoleRelService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignRoles(Integer userId, List<Integer> roleIds) {
        QueryWrapper<UserRoleRel> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        this.baseMapper.delete(qw);
        List<UserRoleRel> userRoleRelList = roleIds.stream()
                .flatMap((Function<Integer, Stream<UserRoleRel>>) roleId -> Stream.of(new UserRoleRel(userId, roleId)))
                .collect(Collectors.toList());
        return this.saveBatch(userRoleRelList);
    }

}
