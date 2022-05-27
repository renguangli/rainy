package com.rainy.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * id,name 进行一些操作(删除，赋权...)时方便记录日志，
 * 不然只能记录id或从数据库中查询
 *
 * @author renguangli
 * @date 2022/5/26 21:03
 */
@Data
public class IdNamesDto {

    @ApiModelProperty("主键id列表")
    @NotEmpty
    private List<Integer> ids;

    @ApiModelProperty(name = "名称", notes = "角色名称,菜单名称（list）...,方便记录日志，调试时可不传。")
    private List<String> names;

}
