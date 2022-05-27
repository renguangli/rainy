package com.rainy.admin.controller;

import cn.dev33.satoken.temp.SaTempUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.RegisterDTO;
import com.rainy.admin.util.ValidateUtils;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.common.Result;
import com.rainy.common.enums.UserConstants;
import com.rainy.core.entity.User;
import com.rainy.core.service.ConfigService;
import com.rainy.core.service.MailService;
import com.rainy.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/11 18:19
 */
@Api(tags = "注册账号")
@ApiSupport(author = "renguangli@bonc.com.cn", order = 1)
@RestController
public class RegisterController {

    @Resource
    private UserService userService;
    @Resource
    private ConfigService configService;
    @Resource
    private MailService mailService;

    @ApiOperation("注册账号")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody RegisterDTO registerDTO) {
        // 校验邮箱是否被注册
        boolean exists = userService.exists("email", registerDTO.getEmail());
        ValidateUtils.isTrue(exists, "邮箱[" + registerDTO.getEmail() + "]已被注册！");
        User user = userService.register(registerDTO.convert());
        // 发送激活账号邮件
        String subject = configService.get(ConfigConstants.ACTIVATE_ACCOUNT_MAIL_TITLE);
        String content = configService.get(ConfigConstants.ACTIVATE_ACCOUNT_MAIL_CONTENT);
        String addr = configService.get(ConfigConstants.ACTIVATE_ACCOUNT_ADDR);
        int timeout = configService.getAsInt(ConfigConstants.TEMP_TOKEN_TIMEOUT);
        String token = SaTempUtil.createToken(user.getId(), timeout);
        addr = addr + token;
        String replace = content.replace("${addr}", addr);
        // 异步发送邮件
        mailService.asyncSendHtml(registerDTO.getEmail(), subject, replace);
        return Result.ok();
    }

    @ApiOperation("激活账号")
    @PostMapping("/activate/{token}")
    public Result activate(@PathVariable String token) {
        // 校验token是否过期
        ValidateUtils.isValidTempToken(token, "账号激活失败，token 已过期,请重新注册！");
        SaTempUtil.deleteToken(token);
        int id = SaTempUtil.parseToken(token, Integer.class);
        User user = new User();
        user.setId(id);
        user.setStatus(UserConstants.STATUS_NORMAL);
        userService.updateById(user);
        return Result.ok();
    }

}
