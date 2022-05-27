package com.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rainy.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户实体类
 *
 * @author renguangli
 * @date 2022/3/10 14:55
 */
@Data
@TableName("t_user")
public class User {

    private static final String PASSWORD_REGEX = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,20}$";

    @ApiModelProperty("主键id")
    @Positive
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("组织id")
    @Positive
    private Integer orgId;

    @ApiModelProperty("职位id")
    @Positive
    private Integer positionId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = PASSWORD_REGEX, message = "密码至少包含大写字母、小写字母、数字或特殊符号中的任意三种!")
    private String password;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("头像")
    @TableField(fill = FieldFill.INSERT)
    private String avatar;

    @ApiModelProperty("邮箱")
    @Email
    @NotBlank
    private String email;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("删除标志")
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;

    @ApiModelProperty("登录次数")
    @TableField(fill = FieldFill.INSERT)
    private Long loginCount;

    @ApiModelProperty("账号过期时间")
    @JsonIgnore
    private LocalDateTime accountExpiredTime;

    @ApiModelProperty("密码过期时间")
    @JsonIgnore
    private LocalDateTime passwordExpiredTime;

    @ApiModelProperty("创建时间")
    @JsonIgnore
    private LocalDateTime createTime;

    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("最后登录ip地址")
    private String lastLoginIp;

    @ApiModelProperty("最后登录浏览器")
    private String browser;

    @ApiModelProperty("最后登录操作系统")
    private String os;

    @ApiModelProperty("角色列表")
    @TableField(exist = false)
    private List<String> roles = new ArrayList<>();

    @ApiModelProperty("权限列表")
    @TableField(exist = false)
    private List<String> permissions = new ArrayList<>();

    public User(){}

    public User(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public Userinfo convertFor() {
        Userinfo userinfo = new Userinfo();
        BeanUtils.copyProperties(this, userinfo);
        return userinfo;
    }
}
