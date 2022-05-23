package com.rainy.task.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.rainy.common.enums.OperationType;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.core.entity.PageInfo;
import com.rainy.task.entity.TaskLog;
import com.rainy.task.service.TaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author renguangli
 * @date 2022/3/28 17:30
 */
@Api(tags = "执行日志")
@RestController
public class TaskLogController {

    @Resource
    private TaskLogService taskLogService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "任务名称"),
            @ApiImplicitParam(name = "group", value = "任务分组"),
            @ApiImplicitParam(name = "success", value = "任务执行结果"),
    })
    @ApiOperation("执行日志列表(分页)")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "定时任务执行日志列表", operationTypeCode = OperationType.QUERY, detail = "'查询了定时任务执行日志列表第' + #page.current + '页.每页' + #page.size + '条数据'", saved = false)
    @GetMapping("/taskLogs")
    public Result pageTaskLogs(PageInfo<TaskLog> page, String name, String group, Boolean success) {
        QueryWrapper<TaskLog> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.eq(StrUtil.isNotBlank(group), "`group`", group);
        qw.eq(success != null, "success", success);
        qw.orderByDesc("datetime");
        if (page.isPaged()) {
            return Result.ok(taskLogService.page(page, qw));
        }
        page.setRecords(taskLogService.list(qw));
        return Result.ok(page);
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
