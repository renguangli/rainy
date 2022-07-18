package com.rainy.power.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.Result;
import com.rainy.common.util.jdbc.ColumnInfo;
import com.rainy.power.entity.PowerForecast;
import com.rainy.power.mapper.DatabaseManager;
import com.rainy.power.service.PowerForecastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/6 12:14
 */
@RestController
public class DatabaseController {

    @Resource
    private DatabaseManager databaseManager;

    @GetMapping("/db/databases")
    public Result listDatabases(){
        return Result.ok(databaseManager.listDatabases());
    }

    @GetMapping("/db/tables")
    public Result listTables(String database){
        return Result.ok(databaseManager.listTables(database));
    }

    @GetMapping("/db/columns")
    public Result listColumns(String database, String table){
        List<ColumnInfo> data = databaseManager.listColumns(database, table);
        return Result.ok(data);
    }

}
