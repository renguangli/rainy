package com.rainy.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private List<Integer> ids;

    @ApiModelProperty("实体名称：role(角色名称),menu(菜单名称)... 列表")
    private List<String> names;

}
