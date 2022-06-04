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

    /** SM3 算法盐 */
    private static final byte[] SM3_SALT = "!@#B#$**@(JH2323erweJKH$#@0".getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String content) {
        return encrypt(content, SM3_SALT);
    }

    public static String encrypt(String content, byte[] salt) {
        return new SM3(salt).digestHex(content);
    }

}
