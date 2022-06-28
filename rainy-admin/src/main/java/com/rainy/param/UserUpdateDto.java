package com.rainy.param;

import com.rainy.sys.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.*;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/29 12:23
 */
@Data
public class UserUpdateDto {

    private static final String PASSWORD_REGEX = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,20}$";

    @ApiModelProperty("主键id")
    @Positive
    private Integer id;

    @ApiModelProperty("组织id")
    @Positive
    private Integer orgId;

    @ApiModelProperty("职位id")
    @Positive
    private Integer positionId;

    @ApiModelProperty("密码")
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = PASSWORD_REGEX, message = "密码至少包含大写字母、小写字母、数字或特殊符号中的任意三种!")
    private String password;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("邮箱")
    @Email
    @NotBlank
    private String email;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("状态")
    private Integer status;

    public User convertFor() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}
