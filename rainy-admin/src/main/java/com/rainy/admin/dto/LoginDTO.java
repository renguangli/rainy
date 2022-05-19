package com.rainy.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/11 18:19
 */
@Data
public class LoginDTO {

    @ApiModelProperty("登录类型")
    private Integer loginType;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String password;

}
