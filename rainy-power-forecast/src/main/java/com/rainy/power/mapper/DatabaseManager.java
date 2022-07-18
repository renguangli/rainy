package com.rainy.power.mapper;

import com.rainy.common.constant.CharConstants;
import com.rainy.common.util.jdbc.ColumnInfo;
import com.rainy.common.util.jdbc.TableInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/8 14:19
 */
@Component
@RequiredArgsConstructor
public class DatabaseManager {

    private final DataSource dataSource;

    public List<String> listDatabases(){
        List<String> databases = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
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

    public List<TableInfo> listTables(String database){
        List<TableInfo> tables = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
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

    public List<ColumnInfo> listColumns(String database, String table){
        List<ColumnInfo> columns = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
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

}
