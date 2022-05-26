package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.AntdvMenu;
import com.rainy.core.entity.Menu;

import java.util.List;

/**
 * rainy
 * Created by renguangli at 2022/1/11 6:32 下午
 *
 * @author renguangli
 * @since JDK1.8
 */
public interface MenuService extends IService<Menu> {

    /**
     * List menus tree list.
     *
     * @return the list
     * @param name String
     * @param typeCode String
     */
    List<Menu> getMenuTree(String name, String typeCode);

    /**
     * List menus tree list.
     *
     * @return the list
     * @param type 菜单类型
     */
    List<Menu> listMenusByType(String type);

    /**
     * List antdv menus list.
     *
     * @return the list
     * @param userId
     */
    List<AntdvMenu> listAntdvMenus(Integer userId);

    List<Menu> listMenusByUserId(Integer userId);

    @Override
    boolean save(Menu menu);

    @Override
    boolean updateById(Menu menu);

    boolean deleteById(Integer id);

    boolean exists(String column, String value);

    List<Menu> listMenusById(Integer id);
}
