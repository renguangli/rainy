package com.rainy.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.dto.IdsNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.common.constant.UserConstants;
import com.rainy.common.util.ValidateUtils;
import com.rainy.core.satoken.SaTokenUtils;
import com.rainy.param.UserUpdateDto;
import com.rainy.sys.entity.PageInfo;
import com.rainy.sys.entity.User;
import com.rainy.sys.entity.UserRoleRel;
import com.rainy.sys.service.ConfigService;
import com.rainy.sys.service.OrgService;
import com.rainy.sys.service.UserRoleRelService;
import com.rainy.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/10 14:44
 */
@Api(tags = "用户管理")
@ApiSupport(author = "renguangli@bonc.com.cn", order = 2)
@RestController
@RequiredArgsConstructor
public class UserController {

    private final OrgService orgService;
    private final UserRoleRelService userRoleRelService;
    private final UserService userService;
    private final UserRoleRelService roleRelService;
    private final ConfigService configService;

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/userinfo")
    public Result userinfo() {
        int userId = SaTokenUtils.getUserId();
        return Result.ok(userService.getById(userId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "name", value = "用户名称")
    })
    @ApiOperation("用户列表")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.QUERY, detail = "'查询了用户列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/users")
    public Result list(PageInfo<User> page, @ApiIgnore User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        if (user.getOrgId() != null) {
            List<Integer> orgIds = orgService.listOrgIdsById(user.getOrgId());
            qw.in("org_id", orgIds);
        }
        qw.likeRight(StrUtil.isNotBlank(user.getUsername()), "username", user.getUsername());
        qw.likeRight(StrUtil.isNotBlank(user.getName()), "name", user.getName());
        if (page.isPaged()) {
            return Result.ok(userService.page(page, qw));
        }
        page.setRecords(userService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("用户列表导出")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.EXPORT, detail = "导出了用户列表", saved = false, paramSaved = false)
    @GetMapping("/users/export")
    public void export(HttpServletResponse response) throws IOException {
        List<User> users = userService.list();
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(users, true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=users.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

    @ApiOperation("新增用户")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.ADD, detail = "'新增了用户[' + #user.username + '].'")
    @ApiOperationSupport(ignoreParameters = {"user.id"})
    @PostMapping("/user")
    public Result save(@RequestBody @Valid User user) {
        // 校验用户是否存在
        boolean exists = userService.exists("username", user.getUsername());
        ValidateUtils.isTrue(exists, "用户[" + user.getUsername() + "]已存在！");
        ValidateUtils.isContains(user.getPassword(), user.getUsername(), "密码不能等于或包含用户名");
        user.setStatus(UserConstants.STATUS_NORMAL);
        return Result.ok(userService.save(user));
    }

    @ApiOperation("删除用户")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.DELETE, detail = "'删除了用户[' + #dto.name + '].'")
    @DeleteMapping("/user")
    public Result remove(@RequestBody @Valid IdNameDto dto) {
        return Result.ok(userService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除用户")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了用户[' + #dto.names + '].'")
    @DeleteMapping("/users")
    public Result batchRemove(@RequestBody @Valid IdNamesDto dto) {
        return Result.ok(userService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("更新用户")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了用户[' + #dto.name + '].'")
    @PutMapping("/user")
    public Result update(@RequestBody @Valid UserUpdateDto dto) {
        return Result.ok(userService.updateById(dto.convertFor()));
    }

    @ApiOperation(value = "重置密码")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.UPDATE, detail = "'重置了用户[' + #dto.name + ']的密码.'")
    @PutMapping("/user/password")
    public Result resetPassword(@RequestBody @Valid IdNameDto dto){
        Integer id = dto.getId();
        User user = userService.getById(id);
        // 校验用户是否存在
        ValidateUtils.isNull(user, "用户[" + id + "]不存在.");
        String resetPassword = configService.get(ConfigConstants.RESET_PASSWORD);
        boolean flag = userService.updateById(new User(id, resetPassword));
        return Result.ok(flag);
    }

    @ApiOperation(value = "根据用户id查询拥有的角色id列表")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.QUERY, detail = "'查询了用户[' + #userId + ']拥有的角色列表'")
    @GetMapping("/user/{userId:[0-9]+}/roleIds")
    public Result listRoleIds(@PathVariable Integer userId){
        QueryWrapper<UserRoleRel> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        List<Integer> roleIds = roleRelService.list(qw).stream()
                .map(UserRoleRel::getRoleId)
                .collect(Collectors.toList());
        return Result.ok(roleIds);
    }

    @ApiOperation(value = "分配角色")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.ADD, detail = "'给用户[' + #dto.name + ']分配了角色[' + #dto.names + '].'")
    @PostMapping("/user/roles")
    public Result assignRoles(@RequestBody @Valid IdsNamesDto dto){
        return Result.ok(userRoleRelService.assignRoles(dto.getId(), dto.getIds()));
    }

}
