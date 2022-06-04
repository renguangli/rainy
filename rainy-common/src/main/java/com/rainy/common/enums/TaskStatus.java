package com.rainy.common.enums;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/4 10:27 AM
 */
public enum TaskStatus {

    STARTUP(0, "启动中"),
    PAUSED(1, "暂停中");

    private final int code;
    private final String name;

    TaskStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
