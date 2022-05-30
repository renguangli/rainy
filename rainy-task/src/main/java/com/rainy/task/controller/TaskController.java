package com.rainy.task.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.ValidateUtils;
import com.rainy.core.entity.PageInfo;
import com.rainy.task.entity.Task;
import com.rainy.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 17:30
 */
@Api(tags = "定时任务")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "任务名称"),
            @ApiImplicitParam(name = "group", value = "任务分组")
    })
    @ApiOperation("任务列表(分页)")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "定时任务管理", operationTypeCode = OperationType.QUERY, detail = "'查询了任务列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/tasks")
    public Result listTasks(PageInfo<Task> page, String name, String group) {
        QueryWrapper<Task> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        qw.eq(StrUtil.isNotBlank(group), "`group`", group);
        if (page.isPaged()) {
            return Result.ok(taskService.page(page, qw));
        }
        page.setRecords(taskService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("新增任务")
    @SysLog(module = "定时任务管理", operationTypeCode = OperationType.ADD, detail = "'新增了任务[' + #task.name + '].'")
    @ApiOperationSupport(ignoreParameters = {"task.id", "task.createTime", "task.updateTime"})
    @PostMapping("/task")
    public Result saveTask(@RequestBody @Valid Task task) throws SchedulerException {
        boolean exists = taskService.exists("name", task.getName());
        ValidateUtils.isTrue(exists, "任务[{" + task.getName() + "}]已存在！");
        return Result.ok(taskService.addTask(task));
    }

    @ApiOperation("删除任务")
    @SysLog(module = "定时任务管理", operationTypeCode = OperationType.DELETE, detail = "'删除了任务[' + #dto.name + '].'")
    @DeleteMapping("/task")
    public Result removeTask(@RequestBody @Valid IdNameDto dto) throws SchedulerException {
        return Result.ok(taskService.removeTaskById(dto.getId()));
    }

    @ApiOperation("批量删除任务")
    @SysLog(module = "定时任务管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了任务[' + #dto.names + '].'")
    @DeleteMapping("/tasks")
    public Result removeTask(@RequestBody @Valid IdNamesDto dto) throws SchedulerException {
        return Result.ok(taskService.batchRemoveTaskById(dto.getIds()));
    }

    @ApiOperation("更新任务")
    @SysLog(module = "定时任务管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了任务[' + #task.name + '].'")
    @ApiOperationSupport(ignoreParameters = {"task.createTime", "task.updateTime"})
    @PutMapping("/task")
    public Result updateTask(@RequestBody @Valid Task task) throws SchedulerException {
        boolean exists = taskService.exists(task.getId(), "name", task.getName());
        ValidateUtils.isTrue(exists, "任务[" + task.getName() + "]已存在！");
        return Result.ok(taskService.updateTaskById(task));
    }

    @ApiOperation("暂停任务")
    @SysLog(module = "定时任务管理", operationTypeCode = OperationType.UPDATE, detail = "'暂停了任务[' + #dto.name + '].'")
    @PutMapping("/task/pause")
    public Result pauseTask(@RequestBody @Valid IdNameDto dto) throws SchedulerException {
        taskService.pauseTask(dto.getId());
        return Result.ok();
    }

    @ApiOperation("恢复任务")
    @SysLog(module = "定时任务管理", operationTypeCode = OperationType.UPDATE, detail = "'恢复了任务[' + #dto.name + '].'")
    @PutMapping("/task/resume")
    public Result resumeTask(@PathVariable @Valid IdNameDto dto) throws SchedulerException {
        taskService.resumeTask(dto.getId());
        return Result.ok();
    }

}
