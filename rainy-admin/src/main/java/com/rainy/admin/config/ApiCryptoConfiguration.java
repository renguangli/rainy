package com.rainy.admin.config;

import com.rainy.api.CryptoFilter;
import com.rainy.crypto.Base64Decryptor;
import com.rainy.crypto.Base64Encryptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/21 18:05
 */
@AutoConfiguration
public class ApiCryptoConfiguration {

    @Bean
    public CryptoFilter cryptoFilter(){
        CryptoFilter filter = new CryptoFilter();
        ArrayList<String> excludeUrls = new ArrayList<>();
        excludeUrls.add("/**/export");
        filter.setExcludeUrls(excludeUrls);
        filter.setDecrypt(true);
        filter.setDecryptor(new Base64Decryptor());
        filter.setEncrypt(true);
        filter.setEncryptor(new Base64Encryptor());
        return filter;
    }

}
