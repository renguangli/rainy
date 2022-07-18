package com.rainy.power.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.jdbc.ColumnInfo;
import com.rainy.power.entity.PowerForecast;
import com.rainy.power.mapper.DatabaseManager;
import com.rainy.power.service.PowerForecastService;
import org.springframework.web.bind.annotation.*;

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
public class PowerForecastController {

    @Resource
    private PowerForecastService powerForecastService;

    @GetMapping("/powerForecasts")
    public Result getPowerForecast(PowerForecast powerForecast){
        QueryWrapper<PowerForecast> qw = new QueryWrapper<>();
        qw.eq("station_code", powerForecast.getStationCode());
        qw.eq("date", powerForecast.getDate());
        List<PowerForecast> list = powerForecastService.list(qw);
        return Result.ok(list);
    }

    @SysLog(module = "功率预测", operationTypeCode = OperationType.UPDATE, detail = "调整了功率预测")
    @PutMapping("/powerForecasts")
    public Result updatePowerForecasts(@RequestBody List<PowerForecast> powerForecasts){
        boolean result = powerForecastService.updateBatchById(powerForecasts);
        return Result.ok(result);
    }

}
