package com.rainy.sso;

import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
