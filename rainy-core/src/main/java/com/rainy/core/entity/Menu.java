package com.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单实体类
 * Created by renguangli at 2022/1/11 6:33 下午
 *
 * @author renguangli
 * @since JDK1.8
 */
@Data
@TableName("t_menu")
public class Menu {

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotNull
    @PositiveOrZero
    private Integer parentId;

    @NotBlank
    @ApiModelProperty("英文名称")
    private String name;

    @NotBlank
    @ApiModelProperty("菜单类型编码")
    private String typeCode;

    @ApiModelProperty("请求路由")
    private String path;

    @ApiModelProperty("vue 组件")
    private String component;

    @ApiModelProperty("描述")
    private String description;

    @NotBlank
    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("内外链url/接口url")
    private String url;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("图标")
    private String icon;

    @NotNull
    @ApiModelProperty("是否展示")
    @TableField("`show`")
    private Boolean show;

    @NotNull
    @Positive
    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("target")
    private String target;

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

    @TableField(exist = false)
    private List<Menu> children;
}
