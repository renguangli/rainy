package com.rainy.sso;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoConsts;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * sso-client-spring-boot-starter
 *
 * @author renguangli
 * @date 2022/5/17 11:16
 */
@Slf4j
public class SsoFilter implements Filter {

    private static final String NAME = "ssoFilter";

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private ObjectMapper objectMapper;
    private SsoProperties ssoProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        for (String skipUrl : ssoProperties.getSkipUrls()) {
            if (antPathMatcher.match(skipUrl, path)) {
                chain.doFilter(request, response);
                return;
            }
        }
        // 1.客户端和服务端都登录了
        if (StpUtil.isLogin() && isServerLogin()) {
            chain.doFilter(request, response);
        }  else {
            if (StpUtil.isLogin()) {
                // 2.客户端登录了，服务端未登录，退出客户端，防止死循环
                StpUtil.logout();
            }
            // 3.跳转到认证中心
            SaHolder.getRequest().forward(req.getContextPath() + SaSsoConsts.Api.ssoLogin);
        }
    }

    public String getName(){
        return NAME;
    }

    public void setSsoProperties(SsoProperties ssoProperties) {
        this.ssoProperties = ssoProperties;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 判断当前用户在认证中心是否登录
     * @return
     * @throws JsonProcessingException
     */
    public boolean isServerLogin() throws JsonProcessingException {
        Object res = SaSsoUtil.getUserinfo(StpUtil.getLoginId());
        SaResult saResult = objectMapper.readValue(res.toString(), SaResult.class);
        return saResult.getData() != null;
    }

}
