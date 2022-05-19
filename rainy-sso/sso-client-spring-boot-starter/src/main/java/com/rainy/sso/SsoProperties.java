package com.rainy.sso;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * sso-client-spring-boot-starter
 *
 * @author renguangli
 * @date 2022/5/16 23:15
 */
@Data
@ConfigurationProperties(prefix = "sso")
public class SsoProperties {

    private boolean enable;
    private String serverUrl;
    private String loginUrl;
    private String secretKey;
    private String logoutCallUrl;
    private List<String> skipUrls;

}
