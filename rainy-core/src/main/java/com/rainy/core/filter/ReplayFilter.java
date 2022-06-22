package com.rainy.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainy.common.Result;
import com.rainy.common.enums.ResultCode;
import com.rainy.sys.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 放重放攻击过滤器
 *
 * @author renguangli
 * @date 2022/3/31 19:16
 */
@Slf4j
@Component
@RequiredArgsConstructor
@WebFilter(urlPatterns = "/**")
public class ReplayFilter implements Filter {
//
//    private static final AntPathMatcher MATCHER = new AntPathMatcher();
//
//    private final ConfigService configService;
//    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
        // todo 重放攻击
        chain.doFilter(req, res);
    }

}
