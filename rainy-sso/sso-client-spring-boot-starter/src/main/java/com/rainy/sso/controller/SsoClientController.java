package com.rainy.sso.controller;

import cn.dev33.satoken.sso.SaSsoHandle;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sso-client-spring-boot-starter
 *
 * @author renguangli
 * @date 2022/5/16 17:19
 */
@RestController
public class SsoClientController {

    /*
     * SSO-Client端：处理所有SSO相关请求
     * 		http://{host}:{port}/sso/login			-- Client端登录地址，接受参数：back=登录后的跳转地址
     * 		http://{host}:{port}/sso/logout			-- Client端单点注销地址（isSlo=true时打开），接受参数：back=注销后的跳转地址
     * 		http://{host}:{port}/sso/logoutCall		-- Client端单点注销回调地址（isSlo=true时打开），此接口为框架回调，开发者无需关心
     */
    @RequestMapping("/sso/*")
    public Object ssoRequest() {
        return SaSsoHandle.clientRequest();
    }

    // 当前是否登录
    @RequestMapping("/isLogin")
    public Object isLogin() {
        return SaResult.data(StpUtil.isLogin());
    }

    // 返回SSO认证中心登录地址
    @RequestMapping("/getSsoAuthUrl")
    public SaResult getSsoAuthUrl(String clientLoginUrl) {
        String serverAuthUrl = SaSsoUtil.buildServerAuthUrl(clientLoginUrl, "");
        return SaResult.data(serverAuthUrl);
    }

    // 根据ticket进行登录
    @RequestMapping("/doLoginByTicket")
    public SaResult doLoginByTicket(String ticket) {
        Object loginId = SaSsoHandle.checkTicket(ticket, "/doLoginByTicket");
        if(loginId != null) {
            StpUtil.login(loginId);
            return SaResult.data(StpUtil.getTokenValue());
        }
        return SaResult.error("无效ticket：" + ticket);
    }


}
