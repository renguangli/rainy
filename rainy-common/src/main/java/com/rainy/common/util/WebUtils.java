package com.rainy.common.util;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.rainy.common.constant.CharConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022 /3/17 09:50
 */
@Slf4j
public final class WebUtils {

    private static final String UNKNOWN = "unknown";
    private static final String UNKNOWN_ZH = "未知";
    private static final String IPV4_LOCAL_IP = "127.0.0.1";
    private static final String IPV6_LOCAL_IP = "0:0:0:0:0:0:0:1";
    private static final List<String> ipHeaders = Arrays.asList("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP");

    private static final String USER_INFO_KEY = "userinfo";

    public static String getRequestURI(){
        return getRequest().getRequestURI();
    }

    public static String getRequestMethod(){
        return getRequest().getMethod();
    }

    /**
     * Get browser
     *
     * @return the browser
     */
    public static String getBrowser(){
        UserAgent userAgent = getUserAgent();
        if (userAgent == null) {
            return UNKNOWN_ZH;
        }
        String name = userAgent.getBrowser().getName();
        if (UNKNOWN.equalsIgnoreCase(name)) {
            return UNKNOWN_ZH;
        }
        return name;
    }

    /**
     * Get browser
     *
     * @return the browser
     */
    public static String getOs(){
        UserAgent userAgent = getUserAgent();
        if (userAgent == null) {
            return UNKNOWN_ZH;
        }
        String name = userAgent.getOs().getName();
        if (UNKNOWN.equalsIgnoreCase(name)) {
            return UNKNOWN_ZH;
        }
        return name;
    }

    public static UserAgent getUserAgent(){
        String userAgentStr = getRequest().getHeader(HttpHeaders.USER_AGENT);
        return UserAgentUtil.parse(userAgentStr);
    }

    public static String getRemoteIp(){
        HttpServletRequest request = getRequest();
        for (String header : ipHeaders) {
            String ip = request.getHeader(header);
            if (isValid(ip)) {
                return ip.split(CharConstants.comma)[0];
            }
        }
        String ip = request.getRemoteAddr();
        return IPV6_LOCAL_IP.equals(ip) ? IPV4_LOCAL_IP : ip;
    }

    private static boolean isValid(String ip) {
        return StrUtil.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip);
    }

    /**
     * 从上下文中获取 HttpServletRequest
     * @return HttpServletRequest
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * 从上下文中获取 HttpServletRequest
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        // 获取 servletRequestAttributes
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new RuntimeException("非 web 上下文，无法获取 Request.");
        }
        return servletRequestAttributes.getRequest();
    }

}
