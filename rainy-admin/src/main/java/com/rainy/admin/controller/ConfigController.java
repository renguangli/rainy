package com.rainy.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.PageInfo;
import com.rainy.admin.service.SaTokenService;
import com.rainy.admin.util.ValidateUtils;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.constant.DictCodeConstants;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.core.entity.Config;
import com.rainy.core.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 13:54
 */
@Api(tags = "配置管理")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
public class ConfigController {

    @Resource
    private ConfigService configService;
    @Resource
    private SaTokenService saTokenService;

    @ApiOperation("配置列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "配置管理", operationTypeCode = OperationType.QUERY, detail = "'查询了配置列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/configs")
    public Result listConfigs(PageInfo<Config> page, String name, String code, String categoryCode){
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

    @ApiOperation("新增配置")
    @PostMapping("/config")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.ADD, detail = "'新增了配置[' + #config.name + '].'")
    public Result saveConfig(@Valid @RequestBody Config config){
        boolean codeExists = configService.exists("code", config.getCode());
        ValidateUtils.isTrue(codeExists, "配置编码[" + config.getCode() + "]已存在！");
        return Result.ok(configService.save(config));
    }

    @ApiOperation("删除配置")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.DELETE, detail = "'删除了配置[' + #dto.name + '].'")
    @DeleteMapping("/config")
    public Result removeConfig(@RequestBody @Valid IdNameDto dto){
        return Result.ok(configService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除配置")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了配置[' + #dto.names + '].'")
    @DeleteMapping("/configs")
    public Result batchRemoveConfig(@RequestBody @Valid IdNamesDto dto){
        return Result.ok(configService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("更新配置")
    @SysLog(module = "配置管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了配置[' + #config.name + '].'")
    @PutMapping("/config")
    public Result updateConfig(@Valid @RequestBody Config config){
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
