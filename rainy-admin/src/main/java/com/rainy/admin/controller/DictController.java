package com.rainy.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.PageInfo;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.ValidateUtils;
import com.rainy.core.entity.Dict;
import com.rainy.core.entity.DictItem;
import com.rainy.core.service.DictItemService;
import com.rainy.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 17:54
 */
@Api(tags = "字典管理")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;
    private final DictItemService dictItemService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_code", value = "分类编码"),
            @ApiImplicitParam(name = "name", value = "字典名称"),
            @ApiImplicitParam(name = "code", value = "唯一编码")
    })
    @ApiOperation("字典列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "字典管理", operationTypeCode = OperationType.QUERY, detail = "'查询了字典列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/dicts")
    public Result listDicts(PageInfo<Dict> page, @ApiIgnore Dict dict) {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq(StrUtil.isNotBlank(dict.getCategoryCode()), "category_code", dict.getCategoryCode());
        qw.likeRight(StrUtil.isNotBlank(dict.getName()), "name", dict.getName());
        qw.likeRight(StrUtil.isNotBlank(dict.getCode()), "code", dict.getCode());
        qw.orderBy(ArrayUtil.isNotEmpty(page.getColumns()), page.isAsc(), page.getColumns());
        if (page.isPaged()) {
            return Result.ok(dictService.page(page, qw));
        }
        page.setRecords(dictService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("添加字典")
    @ApiOperationSupport(ignoreParameters = {"dict.id"})
    @SysLog(module = "字典管理", operationTypeCode = OperationType.ADD, detail = "'新增了字典[' + #dict.name + '].'")
    @PostMapping("/dict")
    public Result saveDict(@RequestBody @Valid Dict dict) {
        boolean nameExists = dictService.exists("name", dict.getName());
        ValidateUtils.isTrue(nameExists, "字典[" + dict.getName() + "]已存在！");
        boolean codeExists = dictService.exists("code", dict.getCode());
        ValidateUtils.isTrue(codeExists, "字典编码[" + dict.getCode() + "]已存在！");
        return Result.ok(dictService.save(dict));
    }

    @ApiOperation("删除字典及字典项")
    @SysLog(module = "字典管理", operationTypeCode = OperationType.DELETE, detail = "'删除了字典[' + #dto.name + '].'")
    @DeleteMapping("/dict")
    public Result removeDict(@RequestBody @Valid IdNameDto dto) {
        return Result.ok(dictService.deleteDictAndItemsById(dto.getId()));
    }

    @ApiOperation("批量删除字典")
    @SysLog(module = "字典管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了字典[' + #dto.names + '].'")
    @DeleteMapping("/dicts")
    public Result batchRemoveDict(@RequestBody @Valid IdNamesDto dto) {
        return Result.ok(dictService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("修改字典")
    @SysLog(module = "字典管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了字典[' + #dict.name + '].'")
    @PutMapping("/dict")
    public Result updateDict(@RequestBody @Valid Dict dict) {
        boolean nameExists = dictService.exists(dict.getId(),"name", dict.getName());
        ValidateUtils.isTrue(nameExists, "字典[" + dict.getName() + "]已存在！");
        boolean codeExists = dictService.exists(dict.getId(), "code", dict.getCode());
        ValidateUtils.isTrue(codeExists, "字典编码[" + dict.getCode() + "]已存在！");
        return Result.ok(dictService.updateById(dict));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "字典编码"),
            @ApiImplicitParam(name = "code", value = "字典项编码")
    })
    @ApiOperation("字典项列表(分页)")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "字典管理", operationTypeCode = OperationType.QUERY, detail = "'查询了字典项列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/dict/items")
    public Result listDictItems(PageInfo<DictItem> page,String dictCode, String code) {
        QueryWrapper<DictItem> qw = new QueryWrapper<>();
        qw.eq(StrUtil.isNotBlank(dictCode), "dict_code", dictCode);
        qw.likeRight(StrUtil.isNotBlank(code), "code", code);
        if (page.isPaged()) {
            return Result.ok(dictItemService.page(page, qw));
        }
        page.setRecords(dictItemService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("新增字典项")
    @ApiOperationSupport(ignoreParameters = {"dictItem.id"})
    @SysLog(module = "字典管理", operationTypeCode = OperationType.ADD, detail = "'新增了字典项[' + #dictItem.code + '].'")
    @PostMapping("/dict/item")
    public Result saveDictItem(@RequestBody @Valid DictItem dictItem) {
        return Result.ok(dictItemService.save(dictItem));
    }

    @ApiOperation("删除字典项")
    @SysLog(module = "字典管理", operationTypeCode = OperationType.DELETE, detail = "'删除了字典项[' + #dto.name + '].'")
    @DeleteMapping("/dict/item")
    public Result removeDictItem(@RequestBody @Valid IdNameDto dto) {
        return Result.ok(dictItemService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除字典项")
    @SysLog(module = "字典管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了字典项[' + #dto.names + '].'")
    @DeleteMapping("/dict/items")
    public Result batchRemoveDictItems(@RequestBody @Valid IdNamesDto dto) {
        return Result.ok(dictItemService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("修改字典项")
    @SysLog(module = "字典管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了字典项[' + #dictItem.code + '].'")
    @PutMapping("/dict/item")
    public Result updateDictItem(@RequestBody @Valid DictItem dictItem) {
        return Result.ok(dictItemService.updateById(dictItem));
    }

}
