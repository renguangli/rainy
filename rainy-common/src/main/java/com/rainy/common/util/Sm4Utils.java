package com.rainy.common.util;

import cn.hutool.crypto.symmetric.SM4;

import java.nio.charset.StandardCharsets;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/17 14:28
 */
public class Sm4Utils {

    private static final byte[] IV = "baf4493a74a996ed".getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String content) {
        return new SM4().encryptHex(content);
    }

    public static String decrypt(String content) {
        return new SM4().decryptStr(content);
    }

    public static String encrypt(String content, String key) {
        return sm4(key).encryptHex(content);
    }

    public static String decrypt(String content, String key) {
        return sm4(key).decryptStr(content);
    }

    private static SM4 sm4(String key){
        return new SM4(key.getBytes(StandardCharsets.UTF_8));
    }

}
