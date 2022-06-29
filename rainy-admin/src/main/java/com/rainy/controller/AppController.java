package com.rainy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.ExcelUtils;
import com.rainy.common.util.ValidateUtils;
import com.rainy.sys.entity.App;
import com.rainy.sys.entity.PageInfo;
import com.rainy.sys.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/4 9:14 PM
 */
@Api(tags = "应用管理")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    @ApiOperation("应用列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "应用管理", operationTypeCode = OperationType.QUERY, detail = "'查询了应用列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/apps")
    public Result list(PageInfo<App> page, String name, String code){
        QueryWrapper<App> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.likeRight(StrUtil.isNotBlank(code), "code", code);
        if (page.isPaged()) {
            return Result.ok(appService.page(page, qw));
        }
        page.setRecords(appService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("应用列表导出")
    @SysLog(module = "应用管理", operationTypeCode = OperationType.EXPORT, detail = "导出了应用列表", saved = false, paramSaved = false)
    @GetMapping("/apps/export")
    public void export(HttpServletResponse response) throws IOException {
        List<App> apps = appService.list();
        ExcelUtils.export(response, apps, "apps.xls");
    }

    @ApiOperation("新增应用")
    @PostMapping("/app")
    @SysLog(module = "应用管理", operationTypeCode = OperationType.ADD, detail = "'新增了应用[' + #app.name + '].'")
    public Result save(@Valid @RequestBody App app){
        boolean nameExists = appService.exists("name", app.getName());
        ValidateUtils.isTrue(nameExists, "应用[{}]已存在！", app.getName());
        boolean codeExists = appService.exists("code", app.getCode());
        ValidateUtils.isTrue(codeExists, "应用编码[{}]已存在！", app.getCode());
        return Result.ok(appService.save(app));
    }

    @ApiOperation("删除应用")
    @SysLog(module = "应用管理", operationTypeCode = OperationType.DELETE, detail = "'删除了应用[' + #dto.name + '].'")
    @DeleteMapping("/app")
    public Result remove(@RequestBody @Valid IdNameDto dto){
        return Result.ok(appService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除应用")
    @SysLog(module = "应用管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了应用[' + #dto.names + '].'")
    @DeleteMapping("/apps")
    public Result batchRemove(@RequestBody @Valid IdNamesDto dto){
        return Result.ok(appService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("更新应用")
    @SysLog(module = "应用管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了应用[' + #app.name + '].'")
    @PutMapping("/app")
    public Result update(@Valid @RequestBody App app){
        boolean nameExists = appService.exists(app.getId(),"name", app.getName());
        ValidateUtils.isTrue(nameExists, "应用[{}}]已存在！", app.getName());
        boolean codeExists = appService.exists(app.getId(),"code", app.getCode());
        ValidateUtils.isTrue(codeExists, "应用编码[{}]已存在！", app.getCode());
        return Result.ok(appService.updateById(app));
    }

}
