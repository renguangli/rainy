package com.rainy.common.util;

import cn.hutool.core.util.StrUtil;
import com.rainy.common.exception.NotExistsException;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/21 16:01
 */
public class ValidateUtils {

    public static void isContains(String str1, String str2, String message, Object... variables) {
        if (str1 == null || str2 == null) {
            return;
        }
        if (str1.contains(str2)) {
            throw new IllegalArgumentException(StrUtil.format(message, variables));
        }
    }

    public static void isNull(Object obj, String message, Object... variables) {
        if (obj == null) {
            throw new NotExistsException(StrUtil.format(message, variables));
        }
    }

    public static void isTrue(boolean expression, String message, Object... variables) {
        if (expression) {
            throw new IllegalArgumentException(StrUtil.format(message, variables));
        }
    }

}
