package com.rainy.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.common.constant.DictCodeConstants;
import com.rainy.common.enums.OperationType;
import com.rainy.common.service.FileService;
import com.rainy.core.satoken.SaTokenUtils;
import com.rainy.sys.entity.App;
import com.rainy.sys.entity.Config;
import com.rainy.sys.entity.Feedback;
import com.rainy.sys.entity.User;
import com.rainy.sys.service.AppService;
import com.rainy.sys.service.ConfigService;
import com.rainy.sys.service.DictService;
import com.rainy.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 17:54
 */
@Api(tags = "通用模块")
@RestController
@RequiredArgsConstructor
public class CommonController {

    private static final String AVATAR_PATH_PREFIX = "/avatar/";

    private final DictService dictService;
    private final ConfigService configService;
    private final UserService userService;
    private final FileService fileService;
    private final AppService appService;

    @ApiOperation("全局配置")
    @GetMapping("/common/config")
    public Result configs() {
        Map<String, Object> data = new HashMap<>();
        // 字典树
        Map<String, Map<String, Object>> dictTree = dictService.getDictTree();
        data.put("dictTree", dictTree);
        // 系统配置
        Map<String, Object> config = new HashMap<>();
        QueryWrapper<Config> qw = new QueryWrapper<>();
        qw.eq("category_code", DictCodeConstants.CONFIG_CATEGORY_FRONT);
        List<Map<String, Object>> configs = configService.listMaps(qw);
        configs.forEach(v -> config.put(v.get("code").toString(), v.get("value")));
        data.put("config", config);
        // 应用
        QueryWrapper<App> appQw = new QueryWrapper<>();
        appQw.select("name", "code");
        List<App> list = appService.list(appQw);
        data.put("apps", list);
        return Result.ok(data);
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
        user.setId(SaTokenUtils.getUserId());
        user.setAvatar(avatar);
        userService.updateById(user);
        return Result.ok(avatar);
    }

    @ApiOperation("头像地址")
    @GetMapping("/avatar/{filename}")
    public void getAvatar(@PathVariable String filename, HttpServletResponse response) throws IOException {
        String uploadPath = configService.get(ConfigConstants.AVATAR_UPLOAD_PATH);
        byte[] bytes = FileUtil.readBytes(uploadPath + File.separator + filename);
        response.getOutputStream().write(bytes);
    }

    @ApiOperation("反馈")
    @PostMapping("/feedback")
    public Result feedback(@RequestBody Feedback feedback) {
        String uploadPath = configService.get(ConfigConstants.AVATAR_UPLOAD_PATH);
        FileUtil.writeString(JSONUtil.toJsonStr(feedback),
                uploadPath + File.separator + feedback.getTimestamp() + ".json",
                StandardCharsets.UTF_8);
        return Result.ok();
    }

}
