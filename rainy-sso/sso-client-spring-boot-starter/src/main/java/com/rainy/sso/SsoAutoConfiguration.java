package com.rainy.sso;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.sso.SaSsoConsts;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

/**
 * sso 客户端自动配置类
 *
 * @author renguangli
 * @date 2022/5/16 23:14
 */
@Slf4j
@Configuration
@ComponentScan({"com.rainy.sso"})
@ConditionalOnProperty(value = "sso.enable", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(SsoProperties.class)
@RequiredArgsConstructor
public class SsoAutoConfiguration {

    private static final String USERINFO_URL = "/sso/userinfo";

    private final SsoProperties ssoProperties;

    @Autowired(required = false)
    public void configSso(@NonNull SaSsoConfig cfg){
        cfg.setIsHttp(true);
        cfg.setIsSlo(true);
        cfg.setAuthUrl(ssoProperties.getLoginUrl());
        cfg.setCheckTicketUrl(ssoProperties.getServerUrl() + SaSsoConsts.Api.ssoCheckTicket);
        cfg.setSloUrl(ssoProperties.getServerUrl() + SaSsoConsts.Api.ssoLogout);
        cfg.setUserinfoUrl(ssoProperties.getServerUrl() + USERINFO_URL);
        cfg.setSecretkey(ssoProperties.getSecretKey());
        cfg.setSsoLogoutCall(ssoProperties.getLogoutCallUrl());

        cfg.setSendHttp(url -> {
            // 发起 http 请求
            log.debug("发起请求 url:{}", url);
            String forObject = restTemplate().getForObject(url, String.class);
            log.debug("请求结果 res:{}", forObject);
            return forObject;
        });
    }

    @Bean
    public FilterRegistrationBean<SsoFilter> filterRegistrationBean(@Autowired(required = false) ObjectMapper objectMapper){
        FilterRegistrationBean<SsoFilter> registration = new FilterRegistrationBean<>();
        SsoFilter ssoFilter = new SsoFilter();
        ssoFilter.setObjectMapper(objectMapper);
        ssoFilter.setSsoProperties(ssoProperties);
        registration.setFilter(ssoFilter);
        registration.setName(ssoFilter.getName());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(30000);
        return new RestTemplate(requestFactory);
    }

}
