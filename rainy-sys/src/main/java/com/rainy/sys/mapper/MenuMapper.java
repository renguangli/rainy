package com.rainy.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainy.sys.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * rainy
 * Created by renguangli at 2022/1/11 6:32 下午
 *
 * @author renguangli
 * @since JDK1.8
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectMenusByUserId(@Param("userId") Integer userId);

    /**
     * 查询按钮类型的菜单
     * @param roleIds 角色id列表，为 null 是返回所有
     * @return 按钮类型的菜单列表
     */
    List<String> selectMenusByRoleIds(@Param("roleIds") List<Integer> roleIds);

}
