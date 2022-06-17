package com.rainy.common.util;

import cn.hutool.crypto.digest.SM3;

import java.nio.charset.StandardCharsets;

/**
 * fgc
 *
 * @author renguangli
 * @date 2022/3/4 10:53
 */
public final class Sm3Utils {

    public static String encrypt(String content) {
        return new SM3().digestHex(content);
    }

    public static String encrypt(String content, String salt) {
        return encrypt(content, salt.getBytes(StandardCharsets.UTF_8));
    }

    public static String encrypt(String content, byte[] salt) {
        return new SM3(salt).digestHex(content);
    }

}
