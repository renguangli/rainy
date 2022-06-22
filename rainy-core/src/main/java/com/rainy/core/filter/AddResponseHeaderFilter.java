package com.rainy.core.filter;

import com.rainy.common.constant.DictCodeConstants;
import com.rainy.sys.entity.DictItem;
import com.rainy.sys.service.DictItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 添加响应 header
 *
 * @author renguangli
 * @date 2022/3/31 19:16
 */
@Slf4j
@Component()
@WebFilter(urlPatterns = "/**")
@Order(value = Integer.MIN_VALUE)
public class AddResponseHeaderFilter implements Filter {

    @Resource
    private DictItemService dictItemService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        List<DictItem> dictItems = dictItemService.listByDictCode(DictCodeConstants.SECURITY_RESPONSE_HEADER_CODE);
        dictItems.forEach(item -> response.setHeader(item.getCode(), item.getValue()));

        List<DictItem> allowMethodDict = dictItemService.listByDictCode(DictCodeConstants.HTTP_ALLOW_METHOD);
        Set<String> allowMethods = allowMethodDict.stream().map(DictItem::getCode).collect(Collectors.toSet());
        if (!allowMethods.contains(request.getMethod().toLowerCase())) {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            return;
        }

        if (CorsUtils.isPreFlightRequest(request)) { // 预检查请求，直接返回
            response.setStatus(HttpStatus.OK.value());
            return;
        }
        chain.doFilter(req, res);
    }

}
