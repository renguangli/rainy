package com.rainy.admin.service;

import cn.dev33.satoken.config.SaTokenConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainy.common.DictCodeConstants;
import com.rainy.core.entity.Config;
import com.rainy.core.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/19 11:39
 */
@Slf4j
@Service
public class SaTokenServiceImpl implements SaTokenService {

    @Resource
    private ConfigService configService;
    @Resource
    private SaTokenConfig saTokenConfig;

    @Override
    @PostConstruct
    public void updateSaTokenConfig() {
        QueryWrapper<Config> qw = new QueryWrapper<>();
        qw.eq("category_code", DictCodeConstants.CONFIG_CATEGORY_SA_TOKEN);
        List<Config> configs = configService.list(qw);
        configs.forEach(config -> {
            try {
                log.info("Init sa-token config: {}={}", config.getCode(), config.getValue());
                ReflectionUtils.setField(SaTokenConfig.class.getDeclaredField(config.getCode()),
                        saTokenConfig,
                        getValueByDataType(config.getValue(), config.getDataType()));
            } catch (NoSuchFieldException e) {
                log.error("Init sa-token config failed: {}={}", config.getCode(), config.getValue(), e);
            }
        });
    }

    private Object getValueByDataType(String value, String dataType) {
        if (boolean.class.getName().equals(dataType)) {
            return Boolean.parseBoolean(value);
        } else if (int.class.getName().equals(dataType)) {
            return Integer.parseInt(value);
        } else if (long.class.getName().equals(dataType)) {
            return Long.parseLong(value);
        } else {
            // string
            return value;
        }
    }
}
