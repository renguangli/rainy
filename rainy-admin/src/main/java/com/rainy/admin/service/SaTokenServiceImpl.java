package com.rainy.admin.service;

import cn.dev33.satoken.config.SaSsoConfig;
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
    @Resource
    private SaSsoConfig ssoConfig;

    @Override
    @PostConstruct
    public void updateSaTokenConfig() {
        List<Config> configs = this.listConfigsByCategoryCode(DictCodeConstants.CONFIG_CATEGORY_SA_TOKEN);
        this.updateConfig(saTokenConfig, configs);
    }


    @Override
    @PostConstruct
    public void updateSsoConfig() {
        List<Config> configs = this.listConfigsByCategoryCode(DictCodeConstants.CONFIG_CATEGORY_SSO);
        this.updateConfig(ssoConfig, configs);
    }

    private void updateConfig(Object ojb, List<Config> configs) {
        configs.forEach(config -> {
            try {
                log.info("Init sa-token config: {}={}", config.getCode(), config.getValue());
                ReflectionUtils.setField(ojb.getClass().getDeclaredField(config.getCode()),
                        ojb,
                        getValueByDataType(config.getValue(), config.getDataType()));
            } catch (NoSuchFieldException e) {
                log.error("Init sa-token config failed: {}={}", config.getCode(), config.getValue(), e);
            }
        });
    }

    private List<Config> listConfigsByCategoryCode(String categoryCode) {
        QueryWrapper<Config> qw = new QueryWrapper<>();
        qw.eq("category_code", categoryCode);
        return configService.list(qw);
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
