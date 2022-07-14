package com.rainy.common.enums;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/26 18:02
 */
public enum DbType {

    MYSQL("MySQL", "com.jdbc.mysql.Driver"),
    ORACLE("Oracle", "com.jdbc.mysql.Driver"),
    HIVE("Hive", "com.jdbc.mysql.Driver");

    private final String name;
    private final String driverClass;

    DbType(String name, String driverClass) {
        this.name = name;
        this.driverClass = driverClass;
    }

    public String getName() {
        return name;
    }

    public String getDriverClass() {
        return driverClass;
    }
}
