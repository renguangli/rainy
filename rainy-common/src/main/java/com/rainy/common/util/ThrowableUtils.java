package com.rainy.common.util;

import com.rainy.common.constant.CharConstants;

/**
 * 异常信息处理工具类
 *
 * @author renguangli
 * @date 2022/4/19 10:50
 */
public class ThrowableUtils {

    /**
     * 转换异常信息为字符串
     * @param e   异常
     */
    public static String toString(Throwable e) {
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            stackTrace.append(stackTraceElement).append(CharConstants.NEW_LINE);
        }
        return e.getClass().getName() + CharConstants.COLON + e.getMessage() + CharConstants.NEW_LINE + stackTrace;
    }

}
