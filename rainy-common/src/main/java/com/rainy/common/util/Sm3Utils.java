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

    public static String digest(String content) {
        return new SM3().digestHex(content);
    }

    public static String digest(String content, String salt) {
        return new SM3(salt.getBytes(StandardCharsets.UTF_8)).digestHex(content);
    }

}
