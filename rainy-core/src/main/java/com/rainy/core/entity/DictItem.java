package com.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 字典项实体类
 *
 * @author renguangli
 * @date 2022/3/21 14:26
 */
@Data
@TableName("t_dict_item")
public class DictItem {


    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank
    @ApiModelProperty("字典编码")
    private String dictCode;

    @NotBlank
    @ApiModelProperty("唯一编码")
    private String code;

    @NotBlank
    @ApiModelProperty("字典项值")
    private String value;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("排序")
    @OrderBy(asc = true)
    private Integer sort;

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
