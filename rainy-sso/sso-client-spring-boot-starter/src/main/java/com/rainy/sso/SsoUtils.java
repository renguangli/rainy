package com.rainy.sso;

import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/19 18:36
 */
@Slf4j
public final class SsoUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Userinfo getUserinfo(){
        Object res = SaSsoUtil.getUserinfo(StpUtil.getLoginId());
        try {
            UserinfoResult userinfoResult = objectMapper.readValue(res.toString(), UserinfoResult.class);
            return userinfoResult.getData();
        } catch (Exception e) {
            log.info("用户信息获取失败!", e);
        }
        return null;
    }

}
