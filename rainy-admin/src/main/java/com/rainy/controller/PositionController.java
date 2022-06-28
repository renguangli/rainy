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
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.ExcelUtils;
import com.rainy.common.util.ValidateUtils;
import com.rainy.sys.entity.PageInfo;
import com.rainy.sys.entity.Position;
import com.rainy.sys.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @date 2022/3/24 17:57
 */
@Api(tags = "职位管理")
@ApiSupport(author = "renguangli@bonc.com.cn", order = 3)
@RestController
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "职位名称")
    })
    @ApiOperation("职位列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "职位管理", operationTypeCode = OperationType.QUERY, detail = "'查询了职位列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/positions")
    public Result list(PageInfo<Position> page, String name) {
        QueryWrapper<Position> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.orderBy(ArrayUtil.isNotEmpty(page.getColumns()), page.isAsc(), page.getColumns());
        if (page.isPaged()) {
            return Result.ok(positionService.page(page, qw));
        }
        page.setRecords(positionService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("职位列表导出")
    @SysLog(module = "职位管理", operationTypeCode = OperationType.EXPORT, detail = "导出了职位列表", saved = false, paramSaved = false)
    @GetMapping("/positions/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Position> positions = positionService.list();
        ExcelUtils.export(response, positions, "positions.xls");
    }

    @ApiOperation("新增职位")
    @SysLog(module = "职位管理", operationTypeCode = OperationType.ADD, detail = "'新增了职位[' + #position.name + '].'")
    @ApiOperationSupport(ignoreParameters = {"id", "createTime", "createBy", "updateTime", "updateBy"})
    @PostMapping("/position")
    public Result save(@RequestBody @Valid Position position) {
        boolean exists = positionService.exists("name", position.getName());
        ValidateUtils.isTrue(exists, "岗位[{}]已存在！", position.getName());
        return Result.ok(positionService.save(position));
    }

    @ApiOperation("删除职位")
    @SysLog(module = "职位管理", operationTypeCode = OperationType.DELETE, detail = "'删除了职位[' + #dto.name + '].'")
    @DeleteMapping("/position")
    public Result remove(@RequestBody @Valid IdNameDto dto) {
        return Result.ok(positionService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除职位")
    @SysLog(module = "职位管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了职位[' + #dto.names + '].'")
    @DeleteMapping("/positions")
    public Result batchRemove(@RequestBody @Valid IdNamesDto dto) {
        return Result.ok(positionService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("更新职位")
    @SysLog(module = "职位管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了职位[' + #position.name + '].'")
    @ApiOperationSupport(ignoreParameters = {"createTime", "createBy", "updateTime", "updateBy"})
    @PutMapping("/position")
    public Result update(@RequestBody @Valid Position position) {
        boolean exists = positionService.exists(position.getId(), "name", position.getName());
        ValidateUtils.isTrue(exists, "岗位[{}}]已存在！", position.getName());
        return Result.ok(positionService.updateById(position));
    }

}
