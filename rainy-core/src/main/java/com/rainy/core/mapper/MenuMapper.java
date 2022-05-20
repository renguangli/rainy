package com.rainy.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainy.core.entity.Menu;
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
    List<String> selectMenusByRoleIds(@Param("roleIds") List<Integer> roleIds);

}
