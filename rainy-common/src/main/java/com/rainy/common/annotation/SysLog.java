package com.rainy.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/23 09:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

    String module() default "";
    String operationTypeCode() default "";
    String detail() default "";
    boolean saved() default true;
    boolean paramSaved() default true;

}
