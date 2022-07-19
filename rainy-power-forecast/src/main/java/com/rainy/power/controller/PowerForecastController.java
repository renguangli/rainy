package com.rainy.power.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.constant.CharConstants;
import com.rainy.common.enums.OperationType;
import com.rainy.common.util.DateUtils;
import com.rainy.common.util.WebUtils;
import com.rainy.power.entity.PowerForecast;
import com.rainy.power.service.PowerForecastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/powerForecasts/download")
    public void downloadPowerForecasts(PowerForecast powerForecast, HttpServletResponse response) throws IOException {
        QueryWrapper<PowerForecast> qw = new QueryWrapper<>();
        qw.eq("station_code", powerForecast.getStationCode());
        qw.eq("date", powerForecast.getDate());

        List<PowerForecast> list = powerForecastService.list(qw);
        String stationCode = powerForecast.getStationCode();
        String model = "国能日新";
        String forecastDatetime = "2022-06-08_18:03:22";
        StringBuilder content = new StringBuilder();
        // 1. 场站基本信息
        String stationInfo = StrUtil.format("//电场名称：{}.{}\t|\t部门：*\t|\t联系人：**\t|\t办公电话：*\t|\t手机：*",
                "山东", "左营华润风电");
        content.append(stationInfo).append(CharConstants.CRLF);
        // 2.单位
        content.append("//**\t单位：MW").append(CharConstants.CRLF);
        // 3.预测厂家
        content.append(StrUtil.format("//预测厂家：{}", model)).append(CharConstants.CRLF);
        // 4.预测时间
        content.append(StrUtil.format("//\t{}", forecastDatetime)).append(CharConstants.CRLF);
        // 5.预测日期
        content.append(StrUtil.format("<!\tEntity={}\ttype=DQ\ttime='{}'\t!>", stationCode, powerForecast.getDate()))
                .append(CharConstants.CRLF);
        // 6.开始标签
        content.append(StrUtil.format("<ShortTermForcast::{}>", stationCode))
                .append(CharConstants.CRLF);
        // 7.列名
        content.append("@@NUM\tNAME\tVALUE\tCAP").append(CharConstants.CRLF);
        // 8. 数据
        int i = 1;
        for (PowerForecast forecast : list) {
            LocalDateTime datetime = forecast.getDatetime();
            long days = Duration.between(LocalDateTime.of(powerForecast.getDate(), LocalTime.MIN), datetime).toDays();
            String value = "VAL" + String.format("%02d", days) + DateUtils.format(datetime, "HHmm");
            String line = "#" + i++ + CharConstants.TABS + value + CharConstants.TABS + forecast.getAdjustValue() + CharConstants.TABS + "199.00" + CharConstants.CRLF;
            content.append(line);
        }
        // 9.结束标签
        content.append(StrUtil.format("</ShortTermForecast::{}>", stationCode));
        byte[] bytes = content.toString().getBytes(CharsetUtil.GBK);
        WebUtils.download(response, "glyc.rb", bytes);
    }

}
