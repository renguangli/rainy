package com.rainy.common.util;

import com.rainy.common.exception.NotExistsException;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/21 16:01
 */
public class ValidateUtils {

    public static void isContains(String str1, String str2, String message) {
        if (str1 == null || str2 == null) {
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

    public static void isGtZero(long count, String message) {
        if (count > 0) {
            throw new IllegalArgumentException(message);
        }
    }

}
