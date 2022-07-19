package com.rainy.core.satoken;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.*;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainy.common.Result;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.common.constant.DictCodeConstants;
import com.rainy.common.enums.ResultCode;
import com.rainy.sys.entity.Menu;
import com.rainy.sys.service.ConfigService;
import com.rainy.sys.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/11 16:51
 */
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(SaTokenConfiguration.class);

    private static final String INCLUDE_PATH_PATTERN = "/**";
    private static final String[] UPDATE_METHODS = {"POST", "PUT", "DELETE"};
    private static final String[] EXCLUDE_PATHS = {
            "/favicon.ico", "/error",
            "/swagger-resources", "/v2/api-docs", "/webjars/**", "/doc.html", "/swagger-ui/index.html", // swagger-path
            "/register", "/activate/*", "/common/config", "/avatar/*", "/captcha/*",
            "/feedback", // 反馈
            "/druid/**", "/powerForecasts/**",
            "/sso/*"// 单点登录
    };

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private MenuService menuService;
    @Resource
    private ConfigService configService;

    /**
     * 单点登录配置
     * @param sso SaSsoConfig
     */
    @Autowired
    public void configSso(SaSsoConfig sso) {
        sso.setSendHttp(url -> {
            try {
                // 发起 http 请求
                return restTemplate.getForObject(url, String.class);
            } catch (Exception e) {
                log.warn("单点退出失败 url:{}", url);
            }
            return null;
        });
    }

    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns(INCLUDE_PATH_PATTERN);
    }

    @Bean
    public SaServletFilter saServletFilter() {
        return new SaServletFilter()
                .addInclude(INCLUDE_PATH_PATTERN)  // 指定认证的请求路径
                .addExclude(EXCLUDE_PATHS)  // 指定放行请求路径
                .setAuth(this::auth) // 认证函数: 每次请求执行
                .setError(this::error) // 异常处理函数：每次认证函数发生异常时执行此函数
                .setBeforeAuth(this::beforeAuth);  // 前置函数：在每次认证函数之前执行
    }

    private void auth(Object r) {
        // 登录验证
        SaRouter.match(INCLUDE_PATH_PATTERN, StpUtil::checkLogin);
        boolean isDemoDev = configService.getAsBoolean(ConfigConstants.IS_DEMO_DEV);
        if (isDemoDev) {
            // 演示环境无法进行增删改操作
            SaRouter.matchMethod(UPDATE_METHODS).check(() -> {
                throw new RuntimeException("演示环境, 无法操作！");
            });
        }
        // 二级认证
//        SaRouter.match("/user", () -> {
//            // 匹配请求方法
//            SaRouter.matchMethod("POST").check(saRouterStaff -> {
//                StpUtil.checkSafe();
//            });
//        });

        // 权限校验
        List<Menu> menus = menuService.listByTypeCode(DictCodeConstants.MENU_TYPE_BUTTON);
        menus.forEach(p -> {
            // 匹配请求uri
            SaRouter.match(p.getUrl(), () -> {
                // 匹配请求方法
                SaRouter.matchMethod(p.getMethod()).check(saRouterStaff -> {
                    // 校验权限
                    StpUtil.checkPermission(p.getName());
                });
            });
        });
    }

    private String error(Throwable e) {
        ResultCode resultCode =  ResultCode.INTERNAL_SERVER_ERROR;
        if (e instanceof NotLoginException || e instanceof DisableLoginException) { // 认证异常
            resultCode = ResultCode.UNAUTHORIZED;
        } else if (e instanceof NotSafeException) { // 二级认证异常
            resultCode = ResultCode.UNAUTHORIZED2;
        } else if (e instanceof NotRoleException || e instanceof NotPermissionException) { // 无权限异常
            resultCode = ResultCode.FORBIDDEN;
        } else { // 其他异常打印日志
            log.error(e.getMessage(), e);
        }
        return buildError(resultCode, e.getMessage());
    }

    private void beforeAuth(Object r) {
        SaHolder.getResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // do something
    }

    private String buildError(ResultCode resultCode, String message) {
        try {
            return objectMapper.writeValueAsString(Result.of(resultCode, message));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }
    }

}
