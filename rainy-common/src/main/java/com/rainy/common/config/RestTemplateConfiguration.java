package com.rainy.common.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate http 请求配置类
 *
 * @author renguangli
 * @date 2022/5/16 23:24
 */
@AutoConfiguration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(10000);
        return new RestTemplate(requestFactory);
    }

}
