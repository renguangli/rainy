package com.rainy.common.encrypt;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 对请求参数，相应结果做解密，加密处理
 */
//@Component
//@WebFilter(urlPatterns = "/*", filterName = "cryptoFilter")
public class CryptoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        EncryptionHttpServletRequestWrapper requestWrapper = new EncryptionHttpServletRequestWrapper(request);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

}
