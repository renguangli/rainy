package com.rainy.power.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.Result;
import com.rainy.power.entity.PowerForecast;
import com.rainy.power.service.PowerForecastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    @PostMapping("/powerForecasts")
    public Result savePowerForecast(String stationCode, LocalDate date){
        FileUtil.readLines("docs/1897f0078a05fb0ccba8b9f4e7e1c054.rb", Charset.forName("GBK"));
        List<PowerForecast> list = powerForecastService.list();
        return Result.ok(list);
    }
}
