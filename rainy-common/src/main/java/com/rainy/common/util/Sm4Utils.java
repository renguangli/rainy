package com.rainy.common.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.SM4;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * fgc
 *
 * @author renguangli
 * @date 2022/3/4 10:53
 */
@Slf4j
public final class Sm4Utils {

    /** SM4 算法 密钥 */
    private static final byte[] KEY = "1111111111111111".getBytes(StandardCharsets.UTF_8);
    private static final byte[] IV = "1111111111111111".getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String str) {
        try {
            return sm4().encryptHex(str);
        } catch (Throwable e) {
            log.warn("Unable to encrypt [{}]!", str);
        }
        return str;
    }

    public static String decrypt(String str) {
        try {
            return sm4().decryptStr(str);
        } catch (Throwable e) {
            log.warn("Unable to decrypt [{}]!", str);
        }
        return str;
    }

    private static SM4 sm4(){
        return new SM4(Mode.CBC, Padding.ZeroPadding, KEY, IV);
    }

}
