package com.rainy.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

/**
 * 定时任务实体类
 *
 * @author renguangli
 * @date 2022/3/28 17:25
 */
@Data
@TableName("t_task")
public class Task {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank
    @ApiModelProperty("cron表达式")
    private String cron;

    @NotBlank
    @ApiModelProperty("class类名")
    private String className;

    @NotBlank
    @ApiModelProperty("任务名称")
    private String name;

    @NotBlank
    @TableField("`group`")
    @ApiModelProperty("任务分组")
    private String group;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty("状态：0启动，1暂停")
    private Integer status;

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
