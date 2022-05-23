package com.rainy.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.PageInfo;
import com.rainy.common.enums.OperationType;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.core.entity.Role;
import com.rainy.core.entity.RoleMenuRel;
import com.rainy.core.service.RoleMenuRelService;
import com.rainy.core.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/14 13:38
 */
@Api(tags = "角色管理")
@ApiSupport(author = "renguangli@bonc.com.cn", order = 4)
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RoleMenuRelService roleMenuRelService;

    @ApiOperation("角色列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "角色管理", operationTypeCode = OperationType.QUERY, detail = "'查询了角色列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/roles")
    public Result listRoles(PageInfo<Role> page, String name, String code) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.likeRight(StrUtil.isNotBlank(code), "code", code);
        qw.orderBy(ArrayUtil.isNotEmpty(page.getColumns()), page.isAsc(), page.getColumns());
        if (page.isPaged()) {
            return Result.ok(roleService.page(page, qw));
        }
        page.setRecords(roleService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("根据角色id查询菜单id列表")
    @GetMapping("/role/{roleId:[0-9]+}/menuIds")
    public Result getMenuIds(@PathVariable Integer roleId) {
        QueryWrapper<RoleMenuRel> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        qw.eq("`all`", true);
        List<RoleMenuRel> list = roleMenuRelService.list(qw);
        List<Integer> menuIds = list.stream()
                .map(RoleMenuRel::getMenuId)
                .collect(Collectors.toList());
        return Result.ok(menuIds);
    }

    @ApiOperation("添加角色")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.ADD, detail = "'新增了角色[' + #role.name + '].'")
    @PostMapping("/role")
    public Result saveRole(@RequestBody @Valid Role role) {
        return Result.ok(roleService.save(role));
    }

    @ApiOperation("删除角色")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.DELETE, detail = "'删除了角色[' + #id + '].'")
    @DeleteMapping("/role/{id:[0-9]+}")
    public Result removeRoleById(@PathVariable Integer id) {
        return Result.ok(roleService.removeById(id));
    }

    @ApiOperation("批量删除角色")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了角色[' + #ids + '].'")
    @DeleteMapping("/roles")
    public Result removeBatchByIds(@RequestBody List<Integer> ids) {
        return Result.ok(roleService.removeBatchByIds(ids));
    }

    @ApiOperation("更新角色")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了角色[' + #role.name + '].'")
    @ApiOperationSupport(ignoreParameters = {"createTime", "createBy", "updateTime", "updateBy"})
    @PutMapping("/role")
    public Result updateRole(@RequestBody @Valid Role role) {
        return Result.ok(roleService.updateById(role));
    }

    @ApiOperation("分配菜单")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.ADD, detail = "'给角色[' + #roleId + ']分配了菜单.'")
    @PostMapping("/role/{roleId:[0-9]+}/menus")
    public boolean assignMenus(@PathVariable Integer roleId, @RequestBody List<RoleMenuRel> roleMenuRelList) {
        return roleService.assignMenus(roleId, roleMenuRelList);
    }

}
