package com.rainy.core.crypto;

import cn.hutool.crypto.symmetric.SM4;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.crypto.Decryptor;
import com.rainy.crypto.Encryptor;
import com.rainy.sys.service.ConfigService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/29 9:40 PM
 */
@Component
public class Sm4Cryptor implements Encryptor, Decryptor, ApplicationContextAware {

    private static final byte[] IV = "baf4493a74a996ed".getBytes(StandardCharsets.UTF_8);

    private ApplicationContext applicationContext;

    @Override
    public String decrypt(String str, Charset charset) {
        return sm4().decryptStr(str, charset);
    }

    @Override
    public String encrypt(String str, Charset charset) {
        return sm4().encryptHex(str,charset);
    }

    private SM4 sm4(){
        ConfigService configService = applicationContext.getBean(ConfigService.class);
        String key = configService.get(ConfigConstants.SM4_KEY);
        SM4 sm4 = new SM4(key.getBytes(StandardCharsets.UTF_8));
        sm4.setIv(IV);
        return sm4;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
