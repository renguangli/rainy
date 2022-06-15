package com.rainy.power;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 清理登录、操作日志定时任务
 *
 * @author renguangli
 * @date 2022/6/15 10:12
 */
@Data
@TableName("t_power_forecast")
public class PowerForecast {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private LocalDate forecastDate;

    private LocalDateTime datetime;

    private Double value;

}
