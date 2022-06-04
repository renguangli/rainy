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
 * 角色实体类
 *
 * @author renguangli
 * @date 2022/3/10 14:55
 */
@Data
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 5385020469270226380L;

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "唯一编码", required = true)
    @NotBlank
    private String code;

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("是否是默认角色")
    private Boolean defaultd;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("删除标志")
    private Boolean deleted;

}
