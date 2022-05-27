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
import com.rainy.core.entity.LoginLog;
import com.rainy.core.service.LoginLogService;
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
@Api(tags = "登录日志")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @ApiOperation("登录日志列表(分页)")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "登录日志", operationTypeCode = OperationType.QUERY, detail = "'查询了登录日志第' + #page.current + '页.每页' + #page.size + '条数据'", saved = false)
    @GetMapping("/loginLogs")
    public Result listOperationLogs(PageInfo<LoginLog> page,
                                                    String username,
                                                    Integer loginType,
                                                    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS) LocalDateTime startTime,
                                                    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS) LocalDateTime endTime){
        QueryWrapper<LoginLog> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(username), "username", username);
        qw.eq(loginType != null, "login_type", loginType);
        qw.between(startTime != null && endTime != null, "datetime", startTime, endTime);
        qw.orderByDesc("datetime");
        if (page.isPaged()) {
            return Result.ok(loginLogService.page(page, qw));
        }
        page.setRecords(loginLogService.list(qw));
        return Result.ok(page);
    }
    @ApiOperation("删除登录日志")
    @SysLog(module = "登录日志", operationTypeCode = OperationType.DELETE, detail = "'删除了登录日志[' + #id + '].'")
    @DeleteMapping("/loginLog/{id:[0-9]+}")
    public Result removeOperationLog(@PathVariable Integer id){
        return Result.ok(loginLogService.removeById(id));
    }

    @ApiOperation("批量删除登录日志")
    @SysLog(module = "登录日志", operationTypeCode = OperationType.DELETE, detail = "'批量删除了登录日志[' + #ids + '].'")
    @DeleteMapping("/loginLogs")
    public Result batchRemoveOperationLog(@RequestBody List<Integer> ids){
        return Result.ok(loginLogService.removeBatchByIds(ids));
    }

    @ApiOperation("清空登录日志")
    @SysLog(module = "登录日志", operationTypeCode = OperationType.DELETE, detail = "清空了登录日志.")
    @DeleteMapping("/loginLogs/clear")
    public Result clearOperationLog(){
        loginLogService.clear();
        return Result.ok();
    }

}
