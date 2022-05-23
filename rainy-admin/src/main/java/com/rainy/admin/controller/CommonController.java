package com.rainy.admin.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.UUID;
import com.rainy.admin.util.WebUtils;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.common.enums.OperationType;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.service.FileService;
import com.rainy.core.entity.User;
import com.rainy.core.service.ConfigService;
import com.rainy.core.service.DictService;
import com.rainy.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 17:54
 */
@Api(tags = "通用模块")
@RestController
public class CommonController {

    private static final String AVATAR_PATH_PREFIX = "/avatar/";

    @Resource
    private DictService dictService;
    @Resource
    private ConfigService configService;
    @Resource
    private UserService userService;
    @Resource
    private FileService fileService;

    @ApiOperation("全局配置")
    @GetMapping("/common/config")
    public Result configs() {
        Map<String, Object> config = new HashMap<>();
        Map<String, Map<String, Object>> dictTree = dictService.getDictTree();
        config.put("dictTree", dictTree);
        config.put("title", configService.get(ConfigConstants.SYS_TITLE));
        config.put("logo", configService.get(ConfigConstants.SYS_LOGO));
        // 是否开启验证码
        config.put("captchaEnable", configService.get(ConfigConstants.CAPTCHA_ENABLE));
        return Result.ok(config);
    }

    @ApiOperation("上传头像")
    @SysLog(module = "头像", operationTypeCode = OperationType.UPDATE, detail = "'上传了头像[' + #file.originalFilename + '].'", paramSaved = false, saved = false)
    @PostMapping("/avatar")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadPath = configService.get(ConfigConstants.AVATAR_UPLOAD_PATH);
        String filename = UUID.fastUUID().toString(true) + FileNameUtil.getSuffix(file.getOriginalFilename());
        String fullPath = uploadPath + File.separator + filename;
        fileService.upload(file.getInputStream(), fullPath);
        String avatar = AVATAR_PATH_PREFIX + filename;
        User user = new User();
        user.setId(WebUtils.getLoginIdAsInt());
        user.setAvatar(avatar);
        userService.updateById(user);
        return Result.ok(avatar);
    }

    @ApiOperation("头像地址")
    @GetMapping("/avatar/{filename}")
    public void avatar(@PathVariable String filename, HttpServletResponse response) throws IOException {
        String uploadPath = configService.get(ConfigConstants.AVATAR_UPLOAD_PATH);
        byte[] bytes = FileUtil.readBytes(uploadPath + File.separator + filename);
        response.getOutputStream().write(bytes);
    }

}
