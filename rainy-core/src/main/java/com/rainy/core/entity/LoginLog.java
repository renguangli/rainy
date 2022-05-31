package com.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志实体类
 *
 * @author renguangli
 * @date 2022/3/11 18:17
 */
@Data
@TableName("t_login_log")
public class LoginLog {

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("登录类型")
    private int loginType;
    @ApiModelProperty("登录时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    @OrderBy(asc = false)
    private LocalDateTime datetime;
    @ApiModelProperty("浏览器")
    private String browser;
    @ApiModelProperty("操作系统")
    private String os;
    @ApiModelProperty("IP地址")
    private String ip;
    @ApiModelProperty("是否成功")
    private boolean success;
    @ApiModelProperty("错误信息")
    private String errorMessage;

}
