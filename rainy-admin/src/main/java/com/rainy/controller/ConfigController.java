package com.rainy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.constant.DictCodeConstants;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.ExcelUtils;
import com.rainy.common.util.ValidateUtils;
import com.rainy.core.satoken.SaTokenService;
import com.rainy.sys.entity.Config;
import com.rainy.sys.entity.PageInfo;
import com.rainy.sys.service.ConfigService;
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
 * @date 2022/3/21 13:54
 */
@Api(tags = "配置管理")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;
    private final SaTokenService saTokenService;

    @ApiOperation("配置列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "配置管理", operationTypeCode = OperationType.QUERY, detail = "'查询了配置列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/configs")
    public Result list(PageInfo<Config> page, String name, String code, String categoryCode){
        QueryWrapper<Config> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.likeRight(StrUtil.isNotBlank(code), "code", code);
        qw.eq(StrUtil.isNotBlank(categoryCode), "category_code", categoryCode);
        if (page.isPaged()) {
            return Result.ok(configService.page(page, qw));
        }
        page.setRecords(configService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("配置列表导出")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.EXPORT, detail = "导出了配置列表", saved = false, paramSaved = false)
    @GetMapping("/configs/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Config> configs = configService.list();
        ExcelUtils.export(response, configs, "configs.xls");
    }

    @ApiOperation("新增配置")
    @PostMapping("/config")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.ADD, detail = "'新增了配置[' + #config.name + '].'")
    public Result save(@Valid @RequestBody Config config){
        boolean nameExists = configService.exists("name", config.getName());
        ValidateUtils.isTrue(nameExists, "配置[{}]已存在！", config.getName());
        boolean codeExists = configService.exists("code", config.getCode());
        ValidateUtils.isTrue(codeExists, "配置编码[{}}]已存在！", config.getCode());
        return Result.ok(configService.save(config));
    }

    @ApiOperation("删除配置")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.DELETE, detail = "'删除了配置[' + #dto.name + '].'")
    @DeleteMapping("/config")
    public Result remove(@RequestBody @Valid IdNameDto dto){
        return Result.ok(configService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除配置")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了配置[' + #dto.names + '].'")
    @DeleteMapping("/configs")
    public Result batchRemove(@RequestBody @Valid IdNamesDto dto){
        return Result.ok(configService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("更新配置")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了配置[' + #config.name + '].'")
    @PutMapping("/config")
    public Result update(@Valid @RequestBody Config config){
        boolean nameExists = configService.exists(config.getId(),"name", config.getName());
        ValidateUtils.isTrue(nameExists, "配置[{}}]已存在！", config.getName());
        boolean codeExists = configService.exists(config.getId(),"code", config.getCode());
        ValidateUtils.isTrue(codeExists, "配置编码[{}}]已存在！", config.getCode());
        boolean ret = configService.update(config);
        // 如果是 sa_token 相关的配置，更新 sa_token 配置
        if (DictCodeConstants.CONFIG_CATEGORY_SA_TOKEN.equals(config.getCategoryCode())) {
            saTokenService.updateSaTokenConfig();
        }
        // 如果是 sa_sso 相关的配置，更新 sa_sso 配置
        if (DictCodeConstants.CONFIG_CATEGORY_SSO.equals(config.getCategoryCode())) {
            saTokenService.updateSsoConfig();
        }
        return Result.ok(ret);
    }

}
