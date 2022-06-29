package com.rainy.sys.service;

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
public interface MenuService extends BaseService<Menu> {

    /**
     * List menus tree list.
     *
     * @return the list
     * @param name String
     * @param typeCode String
     * @param appCode
     */
    List<Menu> getMenuTree(String name, String typeCode, String appCode);

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

}
