package com.rainy.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * id,name 进行一些操作(删除，赋权...)时方便记录日志，
 * 不然只能记录id或从数据库中查询
 *
 * @author renguangli
 * @date 2022/5/26 21:03
 */
@Data
public class IdsNamesDto {

    @ApiModelProperty("主键id")
    @PositiveOrZero
    private Integer id;

    @ApiModelProperty(name = "名称", notes = "角色名称,菜单名称...,方便记录日志，调试时可不传。")
    private String name;

    @ApiModelProperty("id列表")
    private List<Integer> ids;

    @ApiModelProperty(value = "半选id列表", notes = "分配菜单时使用,其他地方不用传")
    private List<Integer> halfIds;

    @ApiModelProperty(name = "名称", notes = "角色名称,菜单名称（list）...,方便记录日志，调试时可不传。")
    private List<String> names;

}
