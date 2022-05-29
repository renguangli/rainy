package com.rainy.admin.config;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.core.service.ConfigService;
import com.rainy.crypto.Decryptor;
import com.rainy.crypto.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/29 9:40 PM
 */
@Component
@RequiredArgsConstructor
public class Sm4Crypto implements Encryptor, Decryptor, ApplicationContextAware {

    private static final byte[] IV = "baf4493a74a996ed".getBytes(StandardCharsets.UTF_8);

    private final JdbcTemplate jdbcTemplate;
    private ApplicationContext applicationContext;

    @Override
    public String decrypt(String str, Charset charset) {
        return sm4().decryptStr(str, charset);
    }

    @Override
    public byte[] decrypt(byte[] bytes) {
        return sm4().decrypt(bytes);
    }

    @Override
    public String encrypt(String str, Charset charset) {
        return sm4().encryptHex(str,charset);
    }

    @Override
    public byte[] encrypt(byte[] bytes) {
        return sm4().encrypt(bytes);
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
