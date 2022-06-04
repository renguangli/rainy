package com.rainy.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.enums.OperationType;
import com.rainy.sys.entity.PageInfo;
import com.rainy.sys.entity.TaskLog;
import com.rainy.sys.service.TaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author renguangli
 * @date 2022/3/28 17:30
 */
@Api(tags = "执行日志")
@RestController
@RequiredArgsConstructor
public class TaskLogController {

    private final TaskLogService taskLogService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "任务名称"),
            @ApiImplicitParam(name = "group", value = "任务分组"),
            @ApiImplicitParam(name = "success", value = "任务执行结果"),
    })
    @ApiOperation("执行日志列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "定时任务执行日志列表", operationTypeCode = OperationType.QUERY, detail = "'查询了定时任务执行日志列表第' + #page.current + '页.每页' + #page.size + '条数据'", saved = false)
    @GetMapping("/taskLogs")
    public Result pageTaskLogs(PageInfo<TaskLog> page, String name, String group, Boolean success) {
        QueryWrapper<TaskLog> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.eq(StrUtil.isNotBlank(group), "`group`", group);
        qw.eq(success != null, "success", success);
        if (page.isPaged()) {
            return Result.ok(taskLogService.page(page, qw));
        }
        page.setRecords(taskLogService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("定时任务执行日志列表导出")
    @SysLog(module = "定时任务管理", operationTypeCode = OperationType.EXPORT, detail = "导出了定时任务执行日志列表", saved = false, paramSaved = false)
    @GetMapping("/taskLogs/export")
    public void export(HttpServletResponse response) throws IOException {
        List<TaskLog> taskLogs = taskLogService.list();
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(taskLogs, true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=taskLogs.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

    @ApiOperation("删除执行日志")
    @SysLog(module = "定时任务执行日志", operationTypeCode = OperationType.DELETE, detail = "'删除了定时任务执行日志[' + #id + '].'")
    @DeleteMapping("/taskLog/{id:[0-9]+}")
    public Result removeTaskLog(@PathVariable Integer id) {
        return Result.ok(taskLogService.removeById(id));
    }

    @ApiOperation("批量删除执行日志")
    @SysLog(module = "定时任务执行日志", operationTypeCode = OperationType.DELETE, detail = "'批量删除了定时任务执行日志[' + #ids + '].'")
    @DeleteMapping("/taskLogs")
    public Result batchRemoveTaskLog(@RequestBody List<Integer> ids) {
        return Result.ok(taskLogService.removeBatchByIds(ids));
    }

    @ApiOperation("清空执行日志")
    @SysLog(module = "定时任务执行日志", operationTypeCode = OperationType.DELETE, detail = "清空了定时任务执行日志")
    @DeleteMapping("/taskLogs/clear")
    public Result clearTaskLog() {
        return Result.ok(taskLogService.clear());
    }

}
