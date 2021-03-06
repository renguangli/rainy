package com.rainy.sys.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统监控实体类
 *
 * @author renguangli
 * @date 2022/3/24 18:42
 */
@Data
public class SysMonitor implements Serializable {

    private static final long serialVersionUID = -657407604916842976L;

    private String osName;

    private String osArch;

    private int availableProcessors;

    private double systemLoadAverage;

    private String javaVersion;
    private String javaVendor;

    @ApiModelProperty("运行时长")
    private long runtime;

    @ApiModelProperty("最大内存")
    private long maxMemory;

    @ApiModelProperty("总内存")
    private long totalMemory;

    @ApiModelProperty("已使用内存")
    private long useMemory;

    @ApiModelProperty("可用内存")
    private long freeMemory;

    @ApiModelProperty("内存使用率")
    private double useRate;

    private String jvmName;
    private String jvmVersion;
}
