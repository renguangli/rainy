package com.rainy.common.util;

import cn.hutool.crypto.symmetric.SM4;

import java.nio.charset.StandardCharsets;

/**
 * fgc
 *
 * @author renguangli
 * @date 2022/3/4 10:53
 */
public final class Sm4Utils {

    /** SM4 算法 密钥 */
    private static final byte[] KEY = "!@#sdf3*(&$hj32$#$**676".getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String content) {
        return new SM4(KEY).encryptHex(content);
    }

    public static String decrypt(String content) {
        return new SM4(KEY).decryptStr(content);
    }

}
