package com.rainy;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/4 5:10 PM
 */
public class RainyApplicationServletInitializer extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RainyApplication.class);
    }

}
