package com.rainy.power.service;

import com.rainy.common.util.jdbc.ColumnInfo;
import com.rainy.common.util.jdbc.TableInfo;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/13 15:49
 */
public interface DbService {

    List<String> dbTree();
    List<String> listDatabases();
    List<TableInfo> listTables(String datasourceName, String database);
    List<ColumnInfo> listColumns(String datasourceName, String database, String table);
}
