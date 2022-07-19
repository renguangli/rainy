package com.rainy.power.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/6 14:49
 */
@Data
@TableName("power.power_forecast{}")
public class PowerForecast {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String stationCode;

    @JsonFormat(pattern = DateUtils.YYYY_MM_DD)
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private LocalDate date;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime datetime;

    private Double value;

    private Double adjustValue;
}
