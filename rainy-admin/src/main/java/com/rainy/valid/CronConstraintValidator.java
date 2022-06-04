package com.rainy.valid;

import org.quartz.CronExpression;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * cron 表达式校验器
 *
 * @author renguangli
 * @date 2022/3/15 19:20
 */
public class CronConstraintValidator implements ConstraintValidator<CronValid,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return CronExpression.isValidExpression(value);
    }

}
