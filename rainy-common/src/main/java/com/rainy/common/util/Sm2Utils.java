package com.rainy.common.util;


import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.engines.SM2Engine;

/**
 * fgc
 *
 * @author renguangli
 * @date 2022/3/4 10:53
 */
public final class Sm2Utils {

    private static final String SM2_PREFIX = "04";

    private static final String PRIVATE_KEY = "308193020100301306072a8648ce3d020106082a811ccf5501822d0479307702010104209eac57eeb25e14c99b3b15d4cd74c0b3751b1173445405754776d8445d7295d7a00a06082a811ccf5501822da144034200044ff389bcf68b228d5d60c2a04d3914ae228517a3ab3c343f37189556f7838fc6f0115b8978df55db34eef7314171482415f227d239bc283de4e92162441ef2f9";
    private static final String PUBLIC_KEY = "3059301306072a8648ce3d020106082a811ccf5501822d034200044ff389bcf68b228d5d60c2a04d3914ae228517a3ab3c343f37189556f7838fc6f0115b8978df55db34eef7314171482415f227d239bc283de4e92162441ef2f9";

    // 前端私钥公钥
    // var privateKey = "9eac57eeb25e14c99b3b15d4cd74c0b3751b1173445405754776d8445d7295d7";
    // var publicKey = "044ff389bcf68b228d5d60c2a04d3914ae228517a3ab3c343f37189556f7838fc6f0115b8978df55db34eef7314171482415f227d239bc283de4e92162441ef2f9";

    public static String encrypt(String content) {
        SM2 sm2 = new SM2(PRIVATE_KEY, PUBLIC_KEY);
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        String str = sm2.encryptHex(content, KeyType.PublicKey);
        return str.substring(2);
    }

    public static String decrypt(String content) {
        SM2 sm2 = new SM2(PRIVATE_KEY, PUBLIC_KEY);
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        return sm2.decryptStr(SM2_PREFIX + content, KeyType.PrivateKey);
    }


}
