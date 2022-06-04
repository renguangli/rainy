package com.rainy.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * 操作日志实体类
 *
 * @author renguangli
 * @date 2022/3/11 18:17
 */
@Data
@TableName("t_operation_log")
public class OperationLog {

    @ApiModelProperty("主键id")
    @Positive
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String module;
    private String operationTypeCode;
    private String username;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    @OrderBy(asc = false)
    private LocalDateTime datetime;
    private String detail;

    private String path;
    private String method;
    private String remoteIp;
    private String browser;
    private String os;

    private String className;
    private String methodName;
    private String params;
    private String result;
    private boolean success;
    private String errorMessage;
    private long processTime;

}
