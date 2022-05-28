package com.rainy.admin.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.util.ValidateUtils;
import com.rainy.admin.util.WebUtils;
import com.rainy.common.enums.OperationType;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.core.entity.Menu;
import com.rainy.core.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * rainy
 * Created by renguangli at 2022/1/11 6:31 下午
 *
 * @since JDK1.8
 */

@Api(tags = "菜单管理")
@ApiSupport(author = "renguangli@bonc.com.cn", order = 6)
@RestController
public class MenuController {

    @Resource
    private MenuService menuService;

    @ApiOperation("antdv菜单列表")
    @GetMapping("/menus/antdv")
    public Result listAntdvMenus(){
        Integer userId = WebUtils.getLoginIdAsInt();
        return Result.ok(menuService.listAntdvMenus(userId));
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
    @SysLog(module = "菜单管理", operationTypeCode = OperationType.DELETE, detail = "'删除了菜单[' + #id + '].'")
    @ApiOperationSupport(ignoreParameters = {"menu.children"})
    @DeleteMapping("/menu/{id:[0-9]+}")
    public Result remove(@PathVariable Integer id){
        return Result.ok(menuService.deleteById(id));
    }

    @ApiOperation("更新菜单")
    @SysLog(module = "菜单管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了菜单[' + #menu.name + '].'")
    @PutMapping("/menu")
    public Result update(@RequestBody @Valid Menu menu){
        return Result.ok(menuService.updateById(menu));
    }

}
