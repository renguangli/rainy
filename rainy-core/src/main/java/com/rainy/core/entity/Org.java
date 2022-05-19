package com.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 组织机构实体类
 *
 * @author renguangli
 * @date 2022/3/16 19:31
 */
@Data
@TableName("t_org")
public class Org {

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(value = "父id", required = true)
    private Integer parentId;

    @NotBlank
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @NotNull
    @Positive
    @ApiModelProperty("排序")
    private Integer sort;

    @Size(max = 128)
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新人")
    private String updateBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("删除标志")
    private Boolean deleted;

    @TableField(exist = false)
    private List<Org> children;

}
