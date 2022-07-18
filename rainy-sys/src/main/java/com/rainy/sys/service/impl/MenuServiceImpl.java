package com.rainy.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.constant.DictCodeConstants;
import com.rainy.common.enums.DefaultRole;
import com.rainy.sys.entity.AntdvMenu;
import com.rainy.sys.entity.Menu;
import com.rainy.sys.entity.Role;
import com.rainy.sys.entity.RoleMenuRel;
import com.rainy.sys.mapper.MenuMapper;
import com.rainy.sys.mapper.RoleMapper;
import com.rainy.sys.mapper.RoleMenuRelMapper;
import com.rainy.sys.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * rainy
 * Created by renguangli at 2022/1/11 6:32 下午
 *
 * @author renguangli
 * @since JDK1.8
 */
@Service
@CacheConfig(cacheNames = "rainy:menu")
@RequiredArgsConstructor
public class MenuServiceImpl
        extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuRelMapper roleMenuRelMapper;
    private final RoleMapper roleMapper;

    @Override
    public List<AntdvMenu> listAntdvMenus(Integer userId, String appCode) {
        List<Menu> menus = this.listByUserIdAndAppCode(userId, appCode);
        return menus.stream()
                .flatMap(this::menu2antdvMenu)
                .collect(Collectors.toList());
    }

    @Override
    public List<Menu> listByUserIdAndAppCode(Integer userId, String appCode) {
        List<Role> roles = roleMapper.listByUserId(userId);
        List<String> roleCodes = roles.stream()
                .map(Role::getCode)
                .collect(Collectors.toList());
        // 如果是超级管理员,返回所有非按钮类型菜单
        if (roleCodes.contains(DefaultRole.SUPPER_ADMIN.getName())) {
            QueryWrapper<Menu> qw = new QueryWrapper<>();
            qw.in("target", DictCodeConstants.MENU_TARGET_TYPES);
            qw.eq(StrUtil.isNotBlank(appCode), "app_code", appCode);
            return this.baseMapper.selectList(qw);
        }
        return this.baseMapper.selectByUserIdAndAppCode(userId, appCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#menu.typeCode")
    public boolean save(Menu menu) {
        Integer parentId = menu.getParentId();
        // 将所有父节点设置为半选状态
        if (parentId != 0) {
            List<Menu> list = list();
            List<Integer> parentIds = new ArrayList<>();
            getParentIds(parentId, list, parentIds);
            QueryWrapper<RoleMenuRel> qw = new QueryWrapper<>();
            qw.in("menu_id", parentIds);
            RoleMenuRel roleMenuRel = new RoleMenuRel();
            roleMenuRel.setAll(false);
            roleMenuRelMapper.update(roleMenuRel, qw);
        }
        return this.baseMapper.insert(menu) > 0;
    }

    @Override
    @CacheEvict(key = "#menu.typeCode")
    public boolean updateById(Menu menu) {
        return this.baseMapper.updateById(menu) > 0;
    }


    @Override
    @Cacheable(key = "#typeCode")
    public List<Menu> listByTypeCode(String typeCode) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.eq(StrUtil.isNotBlank(typeCode), "type_code", typeCode);
        return this.baseMapper.selectList(qw);
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public boolean deleteById(Integer id) {
        // 删除角色菜单关联
        QueryWrapper<RoleMenuRel> qw = new QueryWrapper<>();
        qw.eq("menu_id", id);
        roleMenuRelMapper.delete(qw);
        // 删除菜单
        return this.baseMapper.deleteById(id) > 0;
    }

    private Stream<? extends AntdvMenu> menu2antdvMenu(Menu menu) {
        AntdvMenu m = new AntdvMenu();
        AntdvMenu.Meta meta = new AntdvMenu.Meta();
        BeanUtils.copyProperties(menu, m);
        BeanUtils.copyProperties(menu, meta);
        m.setMeta(meta);
        return Stream.of(m);
    }

    @Override
    public List<Menu> getMenuTree(String name, String typeCode, String appCode) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.eq(StrUtil.isNotBlank(typeCode), "type_code", typeCode);
        qw.eq(StrUtil.isNotBlank(appCode), "app_code", appCode);
        List<Menu> menus = this.list(qw);
        List<Menu> rootMenus = menus.stream()
                .filter(m -> m.getParentId() == null || m.getParentId() == 0).collect(Collectors.toList());

        rootMenus.forEach(m -> {
            List<Menu> children = getChildren(m.getId(), menus);
            m.setChildren(children);
        });
        return rootMenus;
    }

    private List<Menu> getChildren(Integer id, List<Menu> menus) {
        // 过滤出子菜单
        List<Menu> children = menus.stream()
                .filter(m -> id.equals(m.getParentId())).collect(Collectors.toList());
        // 递归设置子菜单的子菜单
        children.forEach(m -> {
            m.setChildren(getChildren(m.getId(), menus));
        });
        return children.isEmpty() ? null: children;
    }

    /**
     * 递归查找子节点下的节点列表
     * @param parentId 父节点id
     * @param menus 列表
     * @return 子节点下的节点列表
     */
    private List<Menu> getOrgsById(Integer parentId, List<Menu> menus) {
        List<Menu> menuListByParentId = new ArrayList<>();
        // 找出 parentId 等于 org.getParentId() 的数据
        for (Menu menu : menus) {
            if (parentId.equals(menu.getParentId())) {
                menuListByParentId.add(menu);
            }
        }
        List<Menu> menuList = new ArrayList<>(menuListByParentId);
        // 递归查找子节点数据
        for (Menu org : menuListByParentId) {
            List<Menu> menusById = getOrgsById(org.getId(), menus);
            if (!menusById.isEmpty()) {
                // 防止并非修改错误，不能直接使用 orgListByParentId 添加
                menuList.addAll(menusById);
            }
        }
        return menuList;
    }

    /**
     * 获取所有父节点 id
     * @param parentId
     * @param menus
     * @param parentIds
     */
    private void getParentIds(Integer parentId, List<Menu> menus, List<Integer> parentIds){
        for (Menu menu : menus) {
            if (Objects.equals(parentId, menu.getId())) {
                if (menu.getId() == 0) {
                   break;
                }
                parentIds.add(menu.getId());
                getParentIds(menu.getParentId(), menus, parentIds);
            }
        }
    }
}
