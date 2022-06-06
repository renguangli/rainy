package com.rainy.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.core.satoken.SaTokenUtils;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.ValidateUtils;
import com.rainy.sys.entity.Menu;
import com.rainy.sys.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * rainy
 * Created by renguangli at 2022/1/11 6:31 下午
 *
 * @since JDK1.8
 */

@Api(tags = "菜单管理")
@ApiSupport(author = "renguangli@bonc.com.cn", order = 6)
@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @ApiOperation("antdv菜单列表")
    @GetMapping("/menus/antdv")
    public Result listAntdvMenus(String appCode){
        Integer userId = SaTokenUtils.getUserId();
        return Result.ok(menuService.listAntdvMenus(userId, appCode));
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "菜单名称")
//    })
//    @ApiOperation("菜单列表")
//    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
//    @SysLog(module = "菜单管理", operationTypeCode = OperationType.QUERY, detail = "'查询了菜单列表第' + #page.current + '页.每页' + #page.size + '条数据'", saved = false)
//    @GetMapping("/menus")
//    public PageInfo<Menu> listMenus(PageInfo<Menu> page, String name){
//        QueryWrapper<Menu> qw = new QueryWrapper<>();
//        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
//        return menuService.page(page);
//    }

    @ApiOperation("菜单列表导出")
    @SysLog(module = "菜单管理", operationTypeCode = OperationType.EXPORT, detail = "导出了菜单列表", saved = false, paramSaved = false)
    @GetMapping("/menus/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Menu> menus = menuService.list();
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(menus, true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=menus.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "菜单名称"),
            @ApiImplicitParam(name = "typeCode", value = "类型编码")
    })
    @ApiOperation("菜单树结构")
    @GetMapping("/menus/tree")
    public Result getMenuTree(String name, String typeCode){
        return Result.ok(menuService.getMenuTree(name, typeCode));
    }

    @ApiOperation("添加菜单")
    @SysLog(module = "菜单管理", operationTypeCode = OperationType.ADD, detail = "'新增了菜单[' + #menu.name + '].'")
    @ApiOperationSupport(ignoreParameters = {"menu.children", "menu.id"})
    @PostMapping("/menu")
    public Result save(@RequestBody @Valid Menu menu){
        boolean exists = menuService.exists("name", menu.getName());
        ValidateUtils.isTrue(exists, "菜单[" + menu.getName() + "]已存在！");
        return Result.ok(menuService.save(menu));
    }

    @ApiOperation("删除菜单")
    @SysLog(module = "菜单管理", operationTypeCode = OperationType.DELETE, detail = "'删除了菜单[' + #dto.name + '].'")
    @ApiOperationSupport(ignoreParameters = {"menu.children"})
    @DeleteMapping("/menu")
    public Result remove(@RequestBody IdNameDto dto){
        boolean exists = menuService.exists("parent_id", dto.getId().toString());
        ValidateUtils.isTrue(exists, "该菜单下有子菜单，请先删除子菜单!");
        return Result.ok(menuService.deleteById(dto.getId()));
    }

    @ApiOperation("更新菜单")
    @SysLog(module = "菜单管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了菜单[' + #menu.name + '].'")
    @PutMapping("/menu")
    public Result update(@RequestBody @Valid Menu menu){
        boolean exists = menuService.exists(menu.getId(), "name", menu.getName());
        ValidateUtils.isTrue(exists, "菜单[" + menu.getName() + "]已存在！");
        return Result.ok(menuService.updateById(menu));
    }

}
