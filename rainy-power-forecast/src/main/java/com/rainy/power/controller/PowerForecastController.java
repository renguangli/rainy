package com.rainy.power.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.constant.CharConstants;
import com.rainy.common.enums.OperationType;
import com.rainy.power.entity.PowerForecast;
import com.rainy.power.service.PowerForecastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @GetMapping("/powerForecasts/download")
    public Result downloadPowerForecasts(PowerForecast powerForecast){
        QueryWrapper<PowerForecast> qw = new QueryWrapper<>();
        qw.eq("station_code", powerForecast.getStationCode());
        qw.eq("date", powerForecast.getDate());
        List<PowerForecast> list = powerForecastService.list(qw);

        String stationInfo = "//电场名称：山东.左营华润风电\t|\t部门：*\t|\t联系人：**	|\t办公电话：*\t|\t手机：*";
        String unitInfo = "//**\t单位：MW";
        String modelName = "//预测厂家：国能日新";
        String forecastDatime = "//\t2022-06-08_18:03:22";
        String entity = "<!\tEntity=ZYHR01\ttype=DQ\ttime='2022-06-09'\t!>";
        StringBuilder content = new StringBuilder();
        content.append("//电场名称：山东.左营华润风电").append(CharConstants.TABS).append(CharConstants.VERTICAL_LINE).append(CharConstants.TABS)
                .append("部门：*").append(CharConstants.TABS).append(CharConstants.VERTICAL_LINE).append(CharConstants.TABS)
                .append("联系人：**").append(CharConstants.TABS).append(CharConstants.VERTICAL_LINE).append(CharConstants.TABS)
                .append("办公电话：*").append(CharConstants.TABS).append(CharConstants.VERTICAL_LINE).append(CharConstants.TABS)
                .append("手机：*")
                .append(CharConstants.NEW_LINE)
                .append("//**").append(CharConstants.TABS).append("单位：MW");
        return Result.ok();
    }

}
