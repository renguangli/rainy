package com.rainy.core.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.sso.exception.SaSsoException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import com.rainy.common.constant.CharConstants;
import com.rainy.common.exception.UnauthorizedException;

/**
 * SaToken 工具类
 *
 * @author renguangli
 * @date 2022/6/4 12:45 AM
 */
public class SaTokenUtils {

    private static final String USER_INFO_KEY = "rainy:userinfo";

    public static String getLoginId(){
        try {
            return StpUtil.getLoginIdAsString();
        } catch (NotLoginException e) { // 未登录异常
            throw new UnauthorizedException(e.getMessage(), e);
        }
    }

    /**
     * 获取用户id
     * @return 用户id
     */
    public static int getUserId(){
        String loginId = getLoginId();
        String[] split = loginId.split(CharConstants.comma);
        return Integer.parseInt(split[0]);
    }

    /**
     * 获取用户id
     * @return 用户id
     */
    public static int getUserId(String loginId){
        String[] split = loginId.split(CharConstants.comma);
        return Integer.parseInt(split[0]);
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    public static String getUsername() {
        String loginId = getLoginId();
        String[] split = loginId.split(CharConstants.comma);
        return split[1];
    }

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
