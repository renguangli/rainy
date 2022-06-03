package com.rainy.admin.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.PageInfo;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.DateUtils;
import com.rainy.core.entity.LoginLog;
import com.rainy.core.entity.Menu;
import com.rainy.core.service.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
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
@Api(tags = "登录日志")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

    @ApiOperation("登录日志列表(分页)")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "登录日志", operationTypeCode = OperationType.QUERY, detail = "'查询了登录日志第' + #page.current + '页.每页' + #page.size + '条数据'", saved = false)
    @GetMapping("/loginLogs")
    public Result list(PageInfo<LoginLog> page,String username,Integer loginType,
                                                    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM) LocalDateTime startTime,
                                                    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM) LocalDateTime endTime){
        QueryWrapper<LoginLog> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(username), "username", username);
        qw.eq(loginType != null, "login_type", loginType);
        qw.between(startTime != null && endTime != null, "datetime", startTime, endTime);
        if (page.isPaged()) {
            return Result.ok(loginLogService.page(page, qw));
        }
        page.setRecords(loginLogService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("登录日志列表导出")
    @SysLog(module = "日志管理", operationTypeCode = OperationType.EXPORT, detail = "导出了登录日志列表", saved = false, paramSaved = false)
    @GetMapping("/loginLogs/export")
    public void export(HttpServletResponse response) throws IOException {
        List<LoginLog> loginLogs = loginLogService.list();
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(loginLogs, true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=loginLogs.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

    @ApiOperation("删除登录日志")
    @SysLog(module = "登录日志", operationTypeCode = OperationType.DELETE, detail = "'删除了登录日志[' + #id + '].'")
    @DeleteMapping("/loginLog/{id:[0-9]+}")
    public Result remove(@PathVariable Integer id){
        return Result.ok(loginLogService.removeById(id));
    }

    @ApiOperation("批量删除登录日志")
    @SysLog(module = "登录日志", operationTypeCode = OperationType.DELETE, detail = "'批量删除了登录日志[' + #ids + '].'")
    @DeleteMapping("/loginLogs")
    public Result batchRemove(@RequestBody List<Integer> ids){
        return Result.ok(loginLogService.removeBatchByIds(ids));
    }

    @ApiOperation("清空登录日志")
    @SysLog(module = "登录日志", operationTypeCode = OperationType.DELETE, detail = "清空了登录日志.")
    @DeleteMapping("/loginLogs/clear")
    public Result clear(){
        loginLogService.clear();
        return Result.ok();
    }

}
