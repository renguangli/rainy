package com.rainy.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用实体类
 *
 * @author renguangli
 * @date 2022/6/4 9:08 PM
 */
@Data
@TableName("t_app")
public class App implements Serializable {

    private static final long serialVersionUID = -2546234898051541106L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("应用名称")
    private String name;

    @ApiModelProperty("唯一编码")
    private String code;

    @ApiModelProperty("重定向地址")
    private String redirect;

    @ApiModelProperty("密钥")
    private String secretKey;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("应用状态：0启用，1禁用")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建者")
    private String createBy;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新者")
    private String updateBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("删除标志")
    private Boolean deleted;

}
