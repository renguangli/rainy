package com.rainy.core.crypto;

import com.rainy.mybatis.DecryptionInterceptor;
import com.rainy.mybatis.EncryptionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 加解密插件配置配置
 *
 * @author renguangli
 * @date 2022/3/28 20:47
 */
@Configuration
public class MybatisCryptoConfiguration {

    @Bean
    public DecryptionInterceptor resultSetInterceptor(@Autowired Sm4Cryptor sm4Crypto){
        return new DecryptionInterceptor(sm4Crypto);
    }

    @Bean
    public EncryptionInterceptor executorInterceptor(@Autowired Sm4Cryptor sm4Crypto){
        return new EncryptionInterceptor(sm4Crypto);
    }
}
