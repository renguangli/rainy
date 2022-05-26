package com.rainy.admin.util;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.sso.exception.SaSsoException;
import cn.dev33.satoken.temp.SaTempUtil;
import com.rainy.common.exception.BizException;
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

    public static void isValidToken(String token){
        long timeout = SaTempUtil.getTimeout(token);
        if (timeout == -2) {
            throw new BizException("账号激活失败，token 已过期！");
        }
    }

    public static void checkPassword(String password){
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new BizException("密码至少包含大写字母、小写字母、数字或特殊符号中的任意三种!");
        }
    }

}
