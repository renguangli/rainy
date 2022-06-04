package com.rainy.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.constant.CharConstants;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.dto.PageInfo;
import com.rainy.sys.entity.User;
import com.rainy.sys.service.SysMonitorService;
import com.rainy.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/24 17:59
 */
@Api(tags = "系统监控")
@ApiSupport(author = "renguangli@bonc.com.cn")
@RestController
@RequiredArgsConstructor
public class SysMonitorController {

    private final SysMonitorService sysMonitorService;
    private final UserService userService;

    @ApiOperation("系统监控")
    @SysLog(module = "系统监控", operationTypeCode = OperationType.QUERY, detail = "'查询了系统监控条数据'")
    @GetMapping("/sys/monitor")
    public Result sysMonitor(){
        return Result.ok(sysMonitorService.getSysMonitor());
    }

    @ApiOperation("在线用户")
    @SysLog(module = "系统监控", operationTypeCode = OperationType.QUERY, detail = "'查询了在线用户第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/users/online")
    public Result listOnlineUsers(PageInfo<User> page, String username, String name){
        List<String> loginIds = StpUtil.searchSessionId(null, -1, 0);
        List<Integer> userIds = loginIds.stream()
                .map(val -> {
                    String loginId = StrUtil.subAfter(val, CharConstants.COLON, true);
                    String userId = loginId.split(CharConstants.comma)[0];
                    return Integer.parseInt(userId);
                })
                .collect(Collectors.toList());
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.in(!userIds.isEmpty(), "id", userIds);
        qw.likeRight(StrUtil.isNotBlank(username), "username", username);
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        return Result.ok(userService.page(page, qw));
    }

    @ApiOperation(value = "强制下线")
    @SysLog(module = "系统监控", operationTypeCode = OperationType.DELETE, detail = "'下线了用户[' + #dto.name + '].'")
    @PutMapping("/user/kickOut")
    public Result kickOut(@RequestBody @Valid IdNameDto dto){
        StpUtil.kickout(dto.getId());
        return Result.ok();
    }

    @ApiOperation(value = "批量强制下线")
    @SysLog(module = "系统监控", operationTypeCode = OperationType.DELETE, detail = "'批量下线了用户[' + #dto.names + '].'")
    @PutMapping("/users/kickOut")
    public Result batchKickOut(@RequestBody @Valid IdNamesDto dto){
        dto.getIds().forEach(StpUtil::kickout);
        return Result.ok();
    }
}
