package com.rainy.admin.config.security;

import com.rainy.common.DictCodeConstants;
import com.rainy.core.entity.DictItem;
import com.rainy.core.service.DictItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 添加响应 header
 *
 * @author renguangli
 * @date 2022/3/31 19:16
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/**")
@Order(value = Integer.MIN_VALUE)
public class AddResponseHeaderFilter implements Filter {

    @Resource
    private DictItemService dictItemService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        List<DictItem> dictItems = dictItemService.listDictItemsByDictCode(DictCodeConstants.DICT_SECURITY_RESPONSE_HEADER_CODE);
        dictItems.forEach(item -> {
            response.setHeader(item.getCode(), item.getValue());
        });
        if (CorsUtils.isPreFlightRequest(request)) { // 预检查请求，直接返回
            response.setStatus(HttpStatus.OK.value());
            return;
        }
        chain.doFilter(req, res);
    }

}
