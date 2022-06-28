package com.rainy.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainy.sys.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * rainy
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
    @Select("select r.id,r.code from t_role r " +
            "        left join t_user_role_rel ur on r.id = ur.role_id " +
            "        where ur.user_id = #{userId} and r.deleted = 0")
    List<Role> listByUserId(Object userId);

}
