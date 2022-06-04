package com.rainy.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配置实体类
 *
 * @author renguangli
 * @date 2022/3/21 11:54
 */
@Data
@TableName("t_config")
public class Config implements Serializable {

    private static final long serialVersionUID = -3052821665138888635L;
    
    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank
    @ApiModelProperty("配置名称")
    private String name;

    @NotBlank
    @ApiModelProperty("分类编码")
    private String categoryCode;

    @NotBlank
    @ApiModelProperty("唯一编码")
    private String code;

    @NotBlank
    @ApiModelProperty("数据类型")
    private String dataType;

    @NotBlank
    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("描述")
    private String description;

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
