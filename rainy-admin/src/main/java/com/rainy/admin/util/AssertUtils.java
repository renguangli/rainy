package com.rainy.admin.util;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.sso.exception.SaSsoException;
import com.rainy.common.exception.UnauthorizedException;
import org.springframework.util.Assert;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/21 16:01
 */
public class AssertUtils extends Assert {

    public static void checkSsoSign(){
        try {
            SaSsoUtil.checkSign(SaHolder.getRequest());
        } catch (SaSsoException e) {
            throw new UnauthorizedException(e.getMessage(), e);
        }
    }

}
