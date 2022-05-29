package com.rainy.admin.config;

import com.rainy.crypto.Base64Decryptor;
import com.rainy.crypto.Base64Encryptor;
import com.rainy.api.CryptoFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/21 18:05
 */
@Configuration
public class ApiDecryptConfig {

    @Bean
    public CryptoFilter cryptoFilter(){
        CryptoFilter filter = new CryptoFilter();
        filter.setExcludeUrls(new ArrayList<>());
        filter.setDecrypt(true);
        filter.setDecryptor(new Base64Decryptor());
        filter.setEncrypt(true);
        filter.setEncryptor(new Base64Encryptor());
        return filter;
    }

    public static void main(String[] args) {
        String aaa = new Base64Decryptor().decrypt("eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoiYWRtaW4iLCJsb2dpblR5cGUiOjF9", StandardCharsets.UTF_8);
        System.out.println(aaa);

    }
}
