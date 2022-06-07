package com.rainy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.sys.entity.AntdvMenu;
import com.rainy.sys.entity.Menu;

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
    List<Menu> listByTypeCode(String type);

    /**
     * List antdv menus list.
     *
     * @return the list
     * @param userId
     * @param appCode
     */
    List<AntdvMenu> listAntdvMenus(Integer userId, String appCode);

    List<Menu> listByUserIdAndAppCode(Integer userId, String appCode);

    @Override
    boolean save(Menu menu);

    @Override
    boolean updateById(Menu menu);

    boolean deleteById(Integer id);

    boolean exists(String column, String value);

    List<Menu> listMenusById(Integer id);

    boolean exists(Integer id, String column, String value);
}
