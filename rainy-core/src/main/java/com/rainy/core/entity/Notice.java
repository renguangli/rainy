package com.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知公告实体类
 *
 * @author renguangli
 * @date 2022/4/28 09:28
 */
@Data
@TableName("t_Notice")
public class Notice {

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("发布者")
    private Integer userId;

    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    @OrderBy(asc = false)
    private LocalDateTime datetime;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

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

    @ApiModelProperty("通知人ids")
    @TableField(exist = false)
    private List<Integer> userIds;

}
