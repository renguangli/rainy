package com.rainy.admin.dto;

import com.rainy.core.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/11 18:19
 */
@Data
public class RegisterDTO {

    @ApiModelProperty("邮箱")
    @NotBlank
    private String email;

    @ApiModelProperty("密码")
    @NotBlank
    private String password;

    public User convert() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}
