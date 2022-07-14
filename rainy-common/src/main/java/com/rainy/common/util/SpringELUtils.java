package com.rainy.common.util;


import com.rainy.common.constant.CharConstants;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * springEL 工具类
 *
 * @author renguangli
 * @date 2022/6/28 10:50
 */
public class SpringELUtils {

    private static final ExpressionParser expressionParser = new SpelExpressionParser();
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public static String resolveSpEL(String spELStr, Method method, Object[] args){
        int i = 0;
        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames != null) {
            for (String parameterName : parameterNames) {
                context.setVariable(parameterName, args[i ++]);
            }
        }
        if (spELStr.contains(CharConstants.HASHTAG)) {
            return expressionParser.parseExpression(spELStr)
                    .getValue(context, String.class);
        }
        return  spELStr;
    }

}
