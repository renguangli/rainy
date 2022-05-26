package com.rainy.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * id,name 进行一些操作(删除，赋权...)时方便记录日志，
 * 不然只能记录id或从数据库中查询
 *
 * @author renguangli
 * @date 2022/5/26 21:03
 */
@Data
public class IdNameDto {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("实体名称：role(角色名称),menu(菜单名称)...")
    private String name;

}
