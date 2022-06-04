package com.rainy.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * cron 表达式校验注解
 *
 * @author renguangli
 * @date 2022/3/30 09:48
 */
@Documented
// 标明这个校验注解是使用哪个校验器进行校验的
@Constraint(validatedBy = { CronConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface CronValid {

    //在 jsr303 中一个注解必须有下面三个属性
    String message() default "表达式格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
