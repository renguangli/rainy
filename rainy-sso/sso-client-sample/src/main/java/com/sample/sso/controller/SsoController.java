package com.sample.sso.controller;

import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainy.sso.SsoUtils;
import com.rainy.sso.Userinfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * rainy-admin
 *
 * @author renguangli
 * @date 2022/5/16 17:19
 */
@RestController
public class SsoController {

    @Resource
    private ObjectMapper objectMapper;
    @Value("${sso.enable}")
    private boolean enable;

    @Value("${sso.server-url}")
    private String serverUrl;

    // SSO-Client端：首页
    @RequestMapping("/")
    public String index() throws JsonProcessingException {
        if (!enable) {
            return "<h2>SSO-Client 应用端</h2>" +
                    "<p>当前应用未开启 sso 单点登录</p>";
        }
        Userinfo userinfo = SsoUtils.getUserinfo();
        return "<h2>SSO-Client 应用端</h2>" +
                "<p>当前会话是否登录：" + StpUtil.isLogin() + "</p>" +
                "<p>当前登录用户头像： <img style='width:40px;height:40px;' src='" + serverUrl + userinfo.getAvatar()  + "'</p>" +
                "<p>当前登录用户：" +userinfo.getUsername() + "(" + userinfo.getName() + ")</p>" +
                "<p><a href=\"javascript:location.href='/sso/login?back=' + location.href;\">登录</a>" +
                " <a href='/sso/logout?back=self'>注销</a></p>";
    }

    // 查询我的账号信息
    @RequestMapping("/userinfo")
    public Object myinfo() {
        return SaSsoUtil.getUserinfo(StpUtil.getLoginId());
    }

    // 全局异常拦截
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        return SaResult.error(e.getMessage());
    }

}
