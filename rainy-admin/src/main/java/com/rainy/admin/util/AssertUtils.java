package com.rainy.admin.util;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.sso.exception.SaSsoException;
import cn.dev33.satoken.temp.SaTempUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainy.common.exception.BizException;
import com.rainy.common.exception.NotExistsException;
import com.rainy.common.exception.UnauthorizedException;

import java.util.regex.Pattern;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/21 16:01
 */
public class AssertUtils {

    // 密码长度8-20位且至少包含大写字母、小写字母、数字或特殊符号中的任意三种
    public static final String PASSWORD_REGEX = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,20}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public static void isContains(String str1, String str2, String message) {
        if (str1 == null || str2 == null ) {
            return;
        }
        if (str1.contains(str2)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object obj, String message) {
        if (obj == null) {
            throw new NotExistsException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkSsoSign(){
        try {
            SaSsoUtil.checkSign(SaHolder.getRequest());
        } catch (SaSsoException e) {
            throw new UnauthorizedException(e.getMessage(), e);
        }
    }

    public static void isValidTempToken(String token, String message){
        long timeout = SaTempUtil.getTimeout(token);
        if (timeout == -2) {
            throw new UnauthorizedException(message);
        }
    }

}
