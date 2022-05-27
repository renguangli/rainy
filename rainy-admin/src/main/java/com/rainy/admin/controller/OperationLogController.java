package com.rainy.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.PageInfo;
import com.rainy.common.enums.OperationType;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.util.DateUtils;
import com.rainy.core.entity.OperationLog;
import com.rainy.core.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    @ApiOperation("操作日志列表(分页)")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "操作日志", operationTypeCode = OperationType.QUERY, detail = "'查询了操作日志第' + #page.current + '页.每页' + #page.size + '条数据'", saved = false)
    @GetMapping("/operationLogs")
    public Result list(PageInfo<OperationLog> page, String username,String operationTypeCode,
                                                    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS) LocalDateTime startTime,
                                                    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS) LocalDateTime endTime){
        QueryWrapper<OperationLog> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(username), "username", username);
        qw.eq(StrUtil.isNotBlank(operationTypeCode), "operation_type_code", operationTypeCode);
        qw.between(startTime != null && endTime != null, "datetime", startTime, endTime);
        qw.orderByDesc("datetime");
        if (page.isPaged()) {
            return Result.ok(operationLogService.page(page, qw));
        }
        page.setRecords(operationLogService.list(qw));
        return Result.ok(page);
    }
    @ApiOperation("删除操作日志")
    @SysLog(module = "操作日志", operationTypeCode = OperationType.DELETE, detail = "'删除了操作日志[' + #id + '].'")
    @DeleteMapping("/operationLog/{id:[0-9]+}")
    public Result remove(@PathVariable Integer id){
        return Result.ok(operationLogService.removeById(id));
    }

    @ApiOperation("批量删除操作日志")
    @SysLog(module = "操作日志", operationTypeCode = OperationType.DELETE, detail = "'批量删除了操作日志[' + #ids + '].'")
    @DeleteMapping("/operationLogs")
    public Result batch(@RequestBody List<Integer> ids){
        return Result.ok(operationLogService.removeBatchByIds(ids));
    }

    @ApiOperation("清空操作日志")
    @SysLog(module = "操作日志", operationTypeCode = OperationType.DELETE, detail = "清空了操作日志.")
    @DeleteMapping("/operationLogs/clear")
    public Result clear(){
        operationLogService.clear();
        return Result.ok();
    }

}
