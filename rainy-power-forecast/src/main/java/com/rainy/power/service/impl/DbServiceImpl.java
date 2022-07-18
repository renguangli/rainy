package com.rainy.power.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.enums.DbType;
import com.rainy.common.util.jdbc.ColumnInfo;
import com.rainy.common.util.jdbc.JdbcUtils;
import com.rainy.common.util.jdbc.TableInfo;
import com.rainy.power.entity.Datasource;
import com.rainy.power.entity.DbTree;
import com.rainy.power.mapper.DatasourceMapper;
import com.rainy.power.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/13 15:50
 */
@Service
@RequiredArgsConstructor
public class DbServiceImpl implements DbService {

    private final DatasourceMapper datasourceMapper;

    @Override
    public List<String> dbTree() {
        List<Datasource> datasourceList = datasourceMapper.selectList(null);
        List<DbTree> dbTrees = datasourceList.stream()
                .map(datasource -> new DbTree(datasource.getName()))
                .collect(Collectors.toList());
        for (DbTree dbTree : dbTrees) {

        }

        return null;
    }

    @Override
    public List<String> listDatabases() {

        return null;
    }

    @Override
    public List<TableInfo> listTables(String datasourceName, String database) {
        QueryWrapper<Datasource> qw = new QueryWrapper<>();
        qw.eq("name", datasourceName);
        Datasource datasource = datasourceMapper.selectOne(qw);
        DbType dbType = DbType.valueOf(datasource.getDbType());
        return JdbcUtils.listTables(dbType.getDriverClass(), datasource.getUrl(), datasource.getUsername(), datasource.getPassword(),
                database);
    }

    @Override
    public List<ColumnInfo> listColumns(String datasourceName, String database, String table) {
        return null;
    }

}
