package com.rainy.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 时间处理工具类
 *
 * @author renguangli
 * @date 2022/3/17 15:08
 */
public final class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static DateTimeFormatter getDtf(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    public static String format(TemporalAccessor datetime) {
        return getDtf(YYYY_MM_DD_HH_MM_SS).format(datetime);
    }

    public static String format(TemporalAccessor datetime, String pattern) {
        return getDtf(pattern).format(datetime);
    }

    public static LocalDateTime parse(String datetime) {
        return LocalDateTime.parse(datetime, getDtf(YYYY_MM_DD_HH_MM_SS));
    }

    public static LocalDateTime parse(String datetime, String pattern) {
        return LocalDateTime.parse(datetime, getDtf(pattern));
    }
}
