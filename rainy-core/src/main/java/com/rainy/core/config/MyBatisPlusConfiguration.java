package com.rainy.core.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.rainy.common.constant.CharConstants;
import com.rainy.common.util.DateUtils;
import com.rainy.common.util.WebUtils;
import com.rainy.core.satoken.SaTokenUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

/**
 * mybatis 相关配置
 *
 * @author renguangli
 * @date 2022/3/28 20:47
 */
@Configuration
@MapperScan(value = {"com.rainy.*.mapper"})
public class MyBatisPlusConfiguration {

    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String CREATE_BY_FIELD = "createBy";
    private static final String UPDATE_TIME_FIELD = "updateTime";
    private static final String UPDATE_BY_FIELD = "updateBy";
    private static final String DELETED_FIELD = "deleted";
    private static final String LOGIN_COUNT_FIELD = "loginCount";

    private static final String DEFAULT_AVATAR = "/avatar/avatar.jpg";
    private static final String AVATAR_FIELD = "avatar";

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //  分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // 动态表名插件
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            if (tableName.contains(CharConstants.BRACES)) {
                int year = DateUtils.getYear();
                return StrUtil.format(tableName, CharConstants.UNDERSCORE + year);
            }
            return tableName;
        });
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return interceptor;
    }

    /**
     *  自动填充时间：创建时间和更新时间,创建人更新人，删除标志
     */
    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName(CREATE_TIME_FIELD, LocalDateTime.now(), metaObject);
                this.setFieldValByName(CREATE_BY_FIELD, SaTokenUtils.getUsername(), metaObject);
                this.setFieldValByName(DELETED_FIELD, false, metaObject);
                this.setFieldValByName(LOGIN_COUNT_FIELD, 0L, metaObject);
                this.setFieldValByName(AVATAR_FIELD, DEFAULT_AVATAR, metaObject);
                this.updateFill(metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName(UPDATE_TIME_FIELD, LocalDateTime.now(), metaObject);
                this.setFieldValByName(UPDATE_BY_FIELD, SaTokenUtils.getUsername(), metaObject);
            }
        };
    }
}
