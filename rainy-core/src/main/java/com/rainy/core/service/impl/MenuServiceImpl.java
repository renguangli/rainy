package com.rainy.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.common.constant.DictCodeConstants;
import com.rainy.common.enums.DefaultRole;
import com.rainy.core.entity.AntdvMenu;
import com.rainy.core.entity.Menu;
import com.rainy.core.entity.Role;
import com.rainy.core.entity.RoleMenuRel;
import com.rainy.core.mapper.MenuMapper;
import com.rainy.core.mapper.RoleMapper;
import com.rainy.core.mapper.RoleMenuRelMapper;
import com.rainy.core.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
@RequiredArgsConstructor
public class MenuServiceImpl
        extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuRelMapper roleMenuRelMapper;
    private final RoleMapper roleMapper;

    @Override
    public List<AntdvMenu> listAntdvMenus(Integer userId) {
        List<Menu> menus = this.listMenusByUserId(userId);
        return menus.stream()
                .flatMap(this::menu2antdvMenu)
                .collect(Collectors.toList());
    }

    @Override
    public List<Menu> listMenusByUserId(Integer userId) {
        List<Role> roles = roleMapper.listRolesByUserId(userId);
        List<String> roleCodes = roles.stream()
                .map(Role::getCode)
                .collect(Collectors.toList());
        // 如果是超级管理员,返回所有非按钮类型菜单
        if (roleCodes.contains(DefaultRole.SUPPER_ADMIN.getName())) {
            QueryWrapper<Menu> qw = new QueryWrapper<>();
            qw.in("target", DictCodeConstants.MENU_TARGET_TYPES);
            return this.baseMapper.selectList(qw);
        }
        return this.baseMapper.selectMenusByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "menu", key = "#menu.typeCode")
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
    @CacheEvict(cacheNames = "menu", key = "#menu.typeCode")
    public boolean updateById(Menu menu) {
        return this.baseMapper.updateById(menu) > 0;
    }


    @Override
    @Cacheable(cacheNames = "menu", key = "#type")
    public List<Menu> listMenusByType(String type) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.eq(StrUtil.isNotBlank(type), "type_code", type);
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

    @Override
    public boolean exists(String column, String value) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

    @Override
    public boolean exists(Integer id, String column, String value) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.ne("id", id);
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

    @Override
    public List<Menu> listMenusById(Integer id) {
        List<Menu> menus = new ArrayList<>();
        // 找出等于id的根节点
        List<Menu> menuList = list();
        Optional<Menu> orgOptional = menuList.stream()
                .filter(o -> id.equals(o.getId()))
                .findFirst();
        if (!orgOptional.isPresent()) {
            return menus;
        }
        menus.add(orgOptional.get());
        // 找出 parentId 等于 id 的数据
        List<Menu> menusById = getOrgsById(id, menuList);
        menus.addAll(menusById);
        return menus;
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
    public List<Menu> getMenuTree(String name, String typeCode) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.eq(StrUtil.isNotBlank(typeCode), "type_code", typeCode);
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
