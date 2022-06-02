package com.rainy.admin.config;

import com.rainy.mybatis.DecryptionInterceptor;
import com.rainy.mybatis.EncryptionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 20:47
 */
@Configuration
public class MyBatisCryptoConfiguration {

    @Bean
    public DecryptionInterceptor resultSetInterceptor(@Autowired Sm4Cryptor sm4Crypto){
        return new DecryptionInterceptor(sm4Crypto);
    }

    @Bean
    public EncryptionInterceptor executorInterceptor(@Autowired Sm4Cryptor sm4Crypto){
        return new EncryptionInterceptor(sm4Crypto);
    }

}
