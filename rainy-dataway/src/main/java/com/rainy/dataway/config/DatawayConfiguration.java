package com.rainy.dataway.config;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * dataway 配置类
 *
 * @author renguangli
 * @date 2022/3/29 09:10
 */
@EnableHasor    // 在Spring 中启用 Hasor
@EnableHasorWeb(path = {"/interface-ui/**", "/api/**"})
@AutoConfiguration
public class DatawayConfiguration {

}
