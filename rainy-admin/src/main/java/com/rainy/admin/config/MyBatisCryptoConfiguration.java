package com.rainy.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.rainy.admin.util.WebUtils;
import com.rainy.mybatis.DecryptionInterceptor;
import com.rainy.mybatis.EncryptionInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/28 20:47
 */
@Configuration
public class MyBatisCryptoConfiguration {

    @Bean
    public DecryptionInterceptor resultSetInterceptor(@Autowired Sm4Crypto sm4Crypto){
        return new DecryptionInterceptor(sm4Crypto);
    }

    @Bean
    public EncryptionInterceptor executorInterceptor(@Autowired Sm4Crypto sm4Crypto){
        return new EncryptionInterceptor(sm4Crypto);
    }

}
