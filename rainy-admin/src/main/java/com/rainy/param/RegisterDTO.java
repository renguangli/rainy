package com.rainy.param;

import com.rainy.sys.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/11 18:19
 */
@Data
public class RegisterDTO {

    private static final String PASSWORD_REGEX = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,20}$";

    @ApiModelProperty("邮箱")
    @Email
    @NotBlank
    private String email;

    @ApiModelProperty("密码")
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = PASSWORD_REGEX, message = "密码至少包含大写字母、小写字母、数字或特殊符号中的任意三种!")
    private String password;

    public User convert() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}
