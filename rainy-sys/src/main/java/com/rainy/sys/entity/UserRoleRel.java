package com.rainy.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色实体类
 *
 * @author renguangli
 * @date 2022/3/25 10:26
 */
@Data
@TableName("t_user_role_rel")
public class UserRoleRel implements Serializable {

    private static final long serialVersionUID = 390194503645813593L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer roleId;

    public UserRoleRel(){}

    public UserRoleRel(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
