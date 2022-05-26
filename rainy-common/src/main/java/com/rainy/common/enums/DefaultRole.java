package com.rainy.common.enums;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/26 18:02
 */
public enum DefaultRole {

    SUPPER_ADMIN("role_superAdmin", "超级管理员"),
    ADMIN("role_admin", "系统管理员"),
    AUDIT("role_audit", "审计管理员");

    private final String name;
    private final String description;

    DefaultRole(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
