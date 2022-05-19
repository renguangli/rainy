package com.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户实体类
 *
 * @author renguangli
 * @date 2022/3/10 14:55
 */
@Data
public class Userinfo {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("登录次数")
    @TableField(fill = FieldFill.INSERT)
    private Long loginCount;

    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("最后登录ip地址")
    private String lastLoginIp;

    @ApiModelProperty("最后登录浏览器")
    private String browser;

    @ApiModelProperty("最后登录操作系统")
    private String os;

    @ApiModelProperty("角色列表")
    private List<String> roles = new ArrayList<>();

    @ApiModelProperty("权限列表")
    private List<String> permissions = new ArrayList<>();

}
