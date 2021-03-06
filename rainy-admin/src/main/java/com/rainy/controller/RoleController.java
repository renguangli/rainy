package com.rainy.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.dto.IdsNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.ExcelUtils;
import com.rainy.common.util.ValidateUtils;
import com.rainy.sys.entity.PageInfo;
import com.rainy.sys.entity.Role;
import com.rainy.sys.entity.RoleMenuRel;
import com.rainy.sys.service.RoleMenuRelService;
import com.rainy.sys.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleMenuRelService roleMenuRelService;

    @ApiOperation("角色列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "角色管理", operationTypeCode = OperationType.QUERY, detail = "'查询了角色列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/roles")
    public Result list(PageInfo<Role> page, String name, String code) {
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

    @ApiOperation("角色列表导出")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.EXPORT, detail = "导出了角色列表", saved = false, paramSaved = false)
    @GetMapping("/roles/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Role> roles = roleService.list();
        ExcelUtils.export(response, roles, "roles.xls");
    }

    @ApiOperation("根据角色id查询菜单id列表")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.QUERY, detail = "'查询了角色[' + #dto.name + ']拥有的菜单列表'")
    @GetMapping("/role/menuIds")
    public Result listMenuIds(IdNameDto dto) {
        QueryWrapper<RoleMenuRel> qw = new QueryWrapper<>();
        qw.eq("role_id", dto.getId());
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
    public Result save(@RequestBody @Valid Role role) {
        boolean nameExists = roleService.exists("name", role.getName());
        ValidateUtils.isTrue(nameExists, "角色[{}]已存在！", role.getName());
        boolean codeExists = roleService.exists("code", role.getCode());
        ValidateUtils.isTrue(codeExists, "角色编码[{}]已存在！", role.getCode());
        return Result.ok(roleService.save(role));
    }

    @ApiOperation("删除角色")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.DELETE, detail = "'删除了角色[' + #dto.name + '].'")
    @DeleteMapping("/role")
    public Result remove(@RequestBody @Valid IdNameDto dto) {
        ValidateUtils.isTrue(roleService.isDefault(dto.getId()), "默认角色不能删除！");
        return Result.ok(roleService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除角色")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了角色[' + #dto.names + '].'")
    @DeleteMapping("/roles")
    public Result removeBatch(@RequestBody @Valid IdNamesDto dto) {
        return Result.ok(roleService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("更新角色")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了角色[' + #role.name + '].'")
    @PutMapping("/role")
    public Result update(@RequestBody @Valid Role role) {
        boolean nameExists = roleService.exists(role.getId(), "name", role.getName());
        ValidateUtils.isTrue(nameExists, "角色[{}}]已存在！", role.getName());
        boolean codeExists = roleService.exists(role.getId(), "code", role.getCode());
        ValidateUtils.isTrue(codeExists, "角色编码[{}}]已存在！", role.getCode());
        return Result.ok(roleService.updateById(role));
    }

    @ApiOperation("分配菜单")
    @SysLog(module = "角色管理", operationTypeCode = OperationType.ADD, detail = "'给角色[' + #dto.name + ']分配了菜单[' + #dto.names + '].'")
    @PostMapping("/role/menus")
    public Result assignMenus(@RequestBody @Valid IdsNamesDto dto) {
        List<RoleMenuRel> roleMenuRels = dto.getIds().stream().
                map((id) -> new RoleMenuRel(dto.getId(),id, true))
                .collect(Collectors.toList());

        //  半选状态菜单
        List<RoleMenuRel> halfRoleMenuRels = dto.getHalfIds().stream().
                map((id) -> new RoleMenuRel(dto.getId(), id, false))
                .collect(Collectors.toList());
        roleMenuRels.addAll(halfRoleMenuRels);
        return Result.ok(roleService.assignMenus(dto.getId(), roleMenuRels));
    }

}
