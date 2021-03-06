//package com.rainy.core.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.rainy.common.Result;
//import com.rainy.common.constant.CharConstants;
//import com.rainy.common.constant.ConfigConstants;
//import com.rainy.common.enums.ResultCode;
//import com.rainy.sys.service.ConfigService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.PathContainer;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.pattern.PathPattern;
//import org.springframework.web.util.pattern.PathPatternParser;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 校验 http header referer
// *
// * @author renguangli
// * @date 2022/3/31 19:16
// */
//@Slf4j
//@Component()
//@WebFilter(urlPatterns = "/**")
//@Order(value = -9999)
//@RequiredArgsConstructor
//public class RefererFilter implements Filter {
//
//    private final ConfigService configService;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//        // 1.忽略的 url
//        String excludeUrl = configService.get(ConfigConstants.HTTP_HEADER_REFERER_EXCLUDE_URL);
//        String[] excludeUrls = excludeUrl.split(CharConstants.comma);
//        for (String url : excludeUrls) {
//            PathPattern pathPattern = PathPatternParser.defaultInstance.parse(request.getContextPath() + url);
//            PathContainer pathContainer = PathContainer.parsePath(request.getRequestURI());
//            if (pathPattern.matches(pathContainer)) {
//                chain.doFilter(req, res);
//                return;
//            }
//        }
//        // 2.验证 referer
//        String referer = configService.get(ConfigConstants.HTTP_HEADER_REFERER);
//        String[] refererList = referer.split(CharConstants.comma);
//        String refererHeader = request.getHeader(HttpHeaders.REFERER);
//        for (String url : refererList) {
//            PathPattern pathPattern = PathPatternParser.defaultInstance.parse(url);
//            if (refererHeader != null && pathPattern.matches(PathContainer.parsePath(refererHeader))) {
//                chain.doFilter(req, res);
//                return;
//            }
//        }
//
//        // 3.验证不通过返回错误信息
//        String body = objectMapper.writeValueAsString(Result.of(ResultCode.ILLEGAL_REQUEST));
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().write(body);
//    }
//
//}
