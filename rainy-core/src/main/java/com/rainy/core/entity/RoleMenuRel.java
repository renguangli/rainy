package com.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色菜单关系实体类
 *
 * @author renguangli
 * @date 2022/3/25 09:59
 */
@Data
@TableName("t_role_menu_rel")
public class RoleMenuRel {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer menuId;
    /**
     * 是否包含所有子菜单
     */
    @TableField("`all`")
    private boolean all;

    public RoleMenuRel(){}

    public RoleMenuRel(Integer roleId, Integer menuId, boolean all) {
        this.roleId = roleId;
        this.menuId = menuId;
        this.all = all;
    }
}
