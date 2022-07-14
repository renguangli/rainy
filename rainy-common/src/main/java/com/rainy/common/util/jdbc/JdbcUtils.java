package com.rainy.common.util.jdbc;

import com.rainy.common.constant.CharConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/12 14:50
 */
public class JdbcUtils {

    public static List<String> listDatabases(String driverClass, String url, String username, String password){
        List<String> databases = new ArrayList<>();
        try (Connection connection = getConnection(driverClass, url, username, password);
             ResultSet rs = connection.getMetaData().getCatalogs();) {
            while (rs.next()) {
                String database = rs.getString(1);
                databases.add(database);
            }
            return databases;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TableInfo> listTables(String driverClass, String url, String username, String password, String database){
        List<TableInfo> tables = new ArrayList<>();
        try (Connection connection = getConnection(driverClass, url, username, password)) {
            ResultSet rs = connection.getMetaData().getTables(database, database, "%", new String[]{});
            TableInfo table = null;
            while (rs.next()) {
                table = new TableInfo();
                String name = rs.getString("TABLE_NAME");
                String remarks = rs.getString("REMARKS");
                table.setName(name);
                table.setRemarks(remarks);
                tables.add(table);
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ColumnInfo> listColumns(String driverClass, String url, String username, String password, String database, String table){
        List<ColumnInfo> columns = new ArrayList<>();
        try (Connection connection = getConnection(driverClass, url, username, password)) {
            ResultSet rs = connection.getMetaData().getColumns(database, database, table, CharConstants.PERCENT_SIGN);
            ColumnInfo column = null;
            while (rs.next()) {
                column = new ColumnInfo();
                int order = rs.getInt("ORDINAL_POSITION");
                String name = rs.getString("COLUMN_NAME");
                String remarks = rs.getString("REMARKS");
                String dataType = rs.getString("TYPE_NAME") +
                        CharConstants.PARENTHESES_PREFIX + rs.getString("DATA_TYPE") + CharConstants.PARENTHESES_SUFFIX;
                String nullable = rs.getString("IS_NULLABLE");
                column.setOrder(order);
                column.setName(name);
                column.setRemarks(remarks);
                column.setDataType(dataType);
                column.setNullable(nullable);
                columns.add(column);
            }
            return columns;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection(String driverClass, String url, String username, String password) throws SQLException {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(url, username, password);
    }

}
