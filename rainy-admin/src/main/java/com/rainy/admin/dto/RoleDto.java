package com.rainy.admin.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.rainy.core.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * 角色更新参数类
 *
 * @author renguangli
 * @date 2022/3/10 14:55
 */
@Data
public class RoleDto {

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

    public Role convert(){
        Role role = new Role();
        BeanUtils.copyProperties(this, role);
        return role;
    }

}
