package com.rainy.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.PageInfo;
import com.rainy.admin.util.WebUtils;
import com.rainy.common.ConfigConstants;
import com.rainy.common.OperationType;
import com.rainy.common.Result;
import com.rainy.common.UserConstants;
import com.rainy.common.annotation.SysLog;
import com.rainy.core.entity.User;
import com.rainy.core.entity.UserRoleRel;
import com.rainy.core.service.ConfigService;
import com.rainy.core.service.OrgService;
import com.rainy.core.service.UserRoleRelService;
import com.rainy.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
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
public class UserController {

    @Resource
    private OrgService orgService;
    @Resource
    private UserRoleRelService userRoleRelService;
    @Resource
    private UserService userService;
    @Resource
    private UserRoleRelService roleRelService;
    @Resource
    private ConfigService configService;

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/userinfo")
    public Result userinfo() {
        return Result.ok(WebUtils.getCurrUserInfo());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "name", value = "用户名称")
    })
    @ApiOperation("用户列表(分页)")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.QUERY, detail = "'查询了用户列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @GetMapping("/users")
    public Result pageUsers(PageInfo<User> page, @ApiIgnore User user) {
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

    @ApiOperation("新增用户")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.ADD, detail = "'新增了用户[' + #user.username + '].'")
    @ApiOperationSupport(ignoreParameters = {"user.id"})
    @PostMapping("/user")
    public Result saveUser(@RequestBody @Valid User user) {
        userService.checkExists("username", user.getUsername(), "用户[" + user.getUsername() + "]已存在！");
        user.setStatus(UserConstants.STATUS_NORMAL);
        return Result.ok(userService.save(user));
    }

    @ApiOperation("删除用户")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.DELETE, detail = "'删除了用户[' + #id + '].'")
    @DeleteMapping("/user/{id:[0-9]+}")
    public Result removeUser(@PathVariable Integer id) {
        return Result.ok(userService.removeById(id));
    }

    @ApiOperation("批量删除用户")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了用户[' + #ids + '].'")
    @DeleteMapping("/users")
    public Result batchRemoveUser(@RequestBody List<Integer> ids) {
        return Result.ok(userService.removeBatchByIds(ids));
    }

    @ApiOperation("更新用户")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了用户[' + #user.name + '].'")
    @PutMapping("/user")
    public Result updateUser(@RequestBody @Valid User user) {
        return Result.ok(userService.updateById(user));
    }

    @ApiOperation(value = "重置密码")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.UPDATE, detail = "'重置了用户[' + #id + ']的密码.'")
    @PutMapping("/user/{id:[0-9]+}/password/reset")
    public Result resetPassword(@PathVariable Integer id){
        User user = userService.getById(id);
        if (user == null) {
            throw new RuntimeException("user[id:" + id + "] not exists.");
        }
        String resetPassword = configService.get(ConfigConstants.RESET_PASSWORD);
        boolean flag = userService.updateById(new User(id, resetPassword));
        return Result.ok(flag);
    }

    @ApiOperation(value = "根据用户id查询拥有的角色id列表")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.QUERY, detail = "'查询了用户[' + #userId + ']拥有的角色列表'")
    @GetMapping("/user/{userId:[0-9]+}/roleIds")
    public Result getRoleIds(@PathVariable Integer userId){
        QueryWrapper<UserRoleRel> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        List<Integer> roleIds = roleRelService.list(qw).stream()
                .map(UserRoleRel::getId)
                .collect(Collectors.toList());
        return Result.ok(roleIds);
    }

    @ApiOperation(value = "分配角色")
    @SysLog(module = "用户管理", operationTypeCode = OperationType.ADD, detail = "'给用户[' + #userId + ']分配了角色[' + #roleIds + '].'")
    @PostMapping("/user/{userId:[0-9]+}/roles")
    public Result assignRoles(@PathVariable Integer userId, @RequestBody Integer[] roleIds){
        return Result.ok(userRoleRelService.assignRoles(userId, roleIds));
    }

}
