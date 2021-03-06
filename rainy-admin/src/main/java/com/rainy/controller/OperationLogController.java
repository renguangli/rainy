package com.rainy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.DateUtils;
import com.rainy.common.util.ExcelUtils;
import com.rainy.sys.entity.OperationLog;
import com.rainy.sys.entity.PageInfo;
import com.rainy.sys.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 13:54
 */
@Api(tags = "操作日志")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
@RequiredArgsConstructor
public class OperationLogController {

    private final OperationLogService operationLogService;

    @ApiOperation("操作日志列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "日志管理", operationTypeCode = OperationType.QUERY, detail = "'查询了操作日志第' + #page.current + '页.每页' + #page.size + '条数据'", saved = false)
    @GetMapping("/operationLogs")
    public Result list(PageInfo<OperationLog> page,String module, String username, String operationTypeCode,
                       @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM) LocalDateTime startTime,
                       @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM) LocalDateTime endTime){
        QueryWrapper<OperationLog> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(username), "username", username);
        qw.likeRight(StrUtil.isNotBlank(module), "module", module);
        qw.eq(StrUtil.isNotBlank(operationTypeCode), "operation_type_code", operationTypeCode);
        qw.between(startTime != null && endTime != null, "datetime", startTime, endTime);
        if (page.isPaged()) {
            return Result.ok(operationLogService.page(page, qw));
        }
        page.setRecords(operationLogService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("操作日志列表导出")
    @SysLog(module = "日志管理", operationTypeCode = OperationType.EXPORT, detail = "导出了操作日志列表", saved = false, paramSaved = false)
    @GetMapping("/operationLogs/export")
    public void export(HttpServletResponse response) throws IOException {
        List<OperationLog> operationLogs = operationLogService.list();
        ExcelUtils.export(response, operationLogs, "operationLogs.xls");
    }

    @ApiOperation("删除操作日志")
    @SysLog(module = "日志管理", operationTypeCode = OperationType.DELETE, detail = "'删除了操作日志[' + #id + '].'")
    @DeleteMapping("/operationLog/{id:[0-9]+}")
    public Result remove(@PathVariable Integer id){
        return Result.ok(operationLogService.removeById(id));
    }

    @ApiOperation("批量删除操作日志")
    @SysLog(module = "日志管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了操作日志[' + #ids + '].'")
    @DeleteMapping("/operationLogs")
    public Result batch(@RequestBody List<Integer> ids){
        return Result.ok(operationLogService.removeBatchByIds(ids));
    }

    @ApiOperation("清空操作日志")
    @SysLog(module = "日志管理", operationTypeCode = OperationType.DELETE, detail = "清空了操作日志.")
    @DeleteMapping("/operationLogs/clear")
    public Result clear(){
        operationLogService.clear();
        return Result.ok();
    }

}
