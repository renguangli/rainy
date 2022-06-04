package com.rainy.util;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.sso.exception.SaSsoException;
import cn.dev33.satoken.temp.SaTempUtil;
import com.rainy.common.exception.UnauthorizedException;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/29 2:24 PM
 */
public class SaTokenUtils {

    public static void checkSsoSign() {
        try {
            SaSsoUtil.checkSign(SaHolder.getRequest());
        } catch (SaSsoException e) {
            throw new UnauthorizedException(e.getMessage(), e);
        }
    }

    public static void isValidTempToken(String token, String message) {
        long timeout = SaTempUtil.getTimeout(token);
        if (timeout == -2) {
            throw new UnauthorizedException(message);
        }
    }
}
