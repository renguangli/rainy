package com.rainy.admin.controller;

import cn.dev33.satoken.sso.SaSsoConsts;
import cn.dev33.satoken.sso.SaSsoHandle;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.sso.exception.SaSsoException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.admin.dto.LoginDTO;
import com.rainy.admin.util.ValidateUtils;
import com.rainy.admin.util.WebUtils;
import com.rainy.admin.vo.Token;
import com.rainy.common.Result;
import com.rainy.common.enums.ResultCode;
import com.rainy.common.enums.UserConstants;
import com.rainy.common.exception.UnauthorizedException;
import com.rainy.core.entity.User;
import com.rainy.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * rainy-admin
 *
 * @author renguangli
 * @date 2022/5/16 17:19
 */
@Api(tags = "单点登录")
@RestController
@RequestMapping("/sso")
@RequiredArgsConstructor
public class SsoServerController {

    private final UserService userService;

    /**
     * 重写 sso 登录逻辑
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestBody LoginDTO loginDTO) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        User user = null;
        if (UserConstants.LOGIN_TYPE_USERNAME == loginDTO.getLoginType()) {
            qw.eq("username", loginDTO.getUsername());
            user = userService.getOne(qw);
        }
        if (UserConstants.LOGIN_TYPE_EMAIL == loginDTO.getLoginType()) {
            qw.eq("email", loginDTO.getEmail());
            user = userService.getOne(qw);
        }
        // todo 密码加密？
        if (user == null || !Objects.equals(user.getPassword(), loginDTO.getPassword())) {
            throw new UnauthorizedException(ResultCode.ACCOUNT_PASSWORD_NOT_MATCH);
        }
        // 是否激活
        if (user.getStatus() == UserConstants.STATUS_NOT_ACTIVATE) {
            throw new UnauthorizedException(ResultCode.ACCOUNT_NOT_ACTIVATE);
        }
        // 账号是否过期
        LocalDateTime now = LocalDateTime.now();
        if (user.getAccountExpiredTime() != null && user.getAccountExpiredTime().isAfter(now)) {
            throw new UnauthorizedException(ResultCode.ACCOUNT_EXPIRED);
        }
        // 密码是否过期
        if (user.getPasswordExpiredTime() != null && user.getPasswordExpiredTime().isAfter(now)) {
            throw new UnauthorizedException(ResultCode.PASSWORD_EXPIRED);
        }

        // 登录
        StpUtil.login(user.getId());

        // 登录成功后更新最后登录ip和时间
        user.setLastLoginIp(WebUtils.getRemoteIp());
        user.setLastLoginTime(LocalDateTime.now());
        user.setBrowser(WebUtils.getBrowser());
        user.setOs(WebUtils.getOs());
        user.setLoginCount(user.getLoginCount() + 1);
        userService.updateById(user);

        // 缓存用户信息
        WebUtils.setUser(user.convertFor());
        Token token = new Token(StpUtil.getTokenName(), StpUtil.getTokenValue(), StpUtil.getTokenTimeout());
        return Result.ok(token);
    }

    /*
     * 校验 ticket
     */
    @ApiOperation("校验 ticket")
    @RequestMapping("/checkTicket")
    public Object checkTicket() {
        return SaSsoHandle.serverRequest();
    }

    /*
     * 处理所有 sso 相关请求
     */
    @ApiOperation("退出登录")
    @RequestMapping("/logout")
    public Object logout() {
        return SaSsoHandle.serverRequest();
    }

    /*
     * 获取用户信息
     */
    @ApiOperation("获取用户信息")
    @GetMapping("/userinfo")
    public Result ssoUserinfo(Integer loginId) {
        // 校验签名
        ValidateUtils.checkSsoSign();
        return Result.ok(WebUtils.getUserinfo(loginId));
    }

    /**
     * 获取 redirectUrl,前后端分离使用
     */
    @ApiOperation("获取 redirectUrl")
    @GetMapping("/redirect")
    private Result getRedirectUrl(String redirect, String mode) {
        // 未登录情况下，返回 code=401
        if (!StpUtil.isLogin()) {
            return Result.unauthorized();
        }
        // 已登录情况下，构建 redirectUrl
        if (SaSsoConsts.MODE_SIMPLE.equals(mode)) {
            // 模式一
            SaSsoUtil.checkRedirectUrl(SaFoxUtil.decoderUrl(redirect));
            return Result.ok(redirect);
        } else {
            // 模式二或模式三
            try {
                String redirectUrl = SaSsoUtil.buildRedirectUrl(StpUtil.getLoginId(), redirect);
                return Result.ok(redirectUrl);
            } catch (SaSsoException e) {
                throw new UnauthorizedException(e.getMessage(), e);
            }
        }
    }

}
