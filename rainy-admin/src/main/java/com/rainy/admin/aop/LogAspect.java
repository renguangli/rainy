package com.rainy.admin.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainy.admin.util.WebUtils;
import com.rainy.common.constant.ConfigConstants;
import com.rainy.common.enums.OperationType;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.util.ThrowableUtils;
import com.rainy.core.entity.OperationLog;
import com.rainy.core.service.ConfigService;
import com.rainy.core.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/14 16:36
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    // 操作日志切入点
    private static final String OPERATION_LOG_POINTCUT = "@annotation(com.rainy.common.annotation.SysLog)";

    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private OperationLogService operationLogService;
    @Resource
    private ConfigService configService;

    /**
     * 打印方法执行时常
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     * @throws Throwable Throwable
     */
    @Around(OPERATION_LOG_POINTCUT)
    public Object printMethodExecuteTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        // 下线自己时，在下线之前获取用户名
        String username = WebUtils.getUsername();
        OperationLog opLog = new OperationLog();
        opLog.setUsername(username);
        opLog.setSuccess(true);
        long startTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            opLog.setSuccess(false);
            opLog.setErrorMessage(ThrowableUtils.toString(e));
            throw e;
        } finally {
            opLog.setProcessTime(System.currentTimeMillis() - startTime);
            this.saveLog(joinPoint, result, opLog);
        }
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, Object result, OperationLog opLog) throws JsonProcessingException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        boolean queryLogSaved = configService.getAsBoolean(ConfigConstants.QUERY_LOG_SAVED);
        // 是否保存查询操作日志
        if (OperationType.QUERY.equals(sysLog.operationTypeCode()) && !queryLogSaved ) {
            return;
        }

        opLog.setDatetime(LocalDateTime.now());
        opLog.setModule(sysLog.module());
        opLog.setOperationTypeCode(sysLog.operationTypeCode());
        String detail = resolveSpEL(sysLog.detail(), method, joinPoint.getArgs());
        opLog.setDetail(detail);

        opLog.setPath(WebUtils.getRequestURI());
        opLog.setMethod(WebUtils.getRequestMethod());
        opLog.setRemoteIp(WebUtils.getRemoteIp());
        opLog.setBrowser(WebUtils.getBrowser());
        opLog.setOs(WebUtils.getOs());

        // 获取请求参数
        String className = joinPoint.getTarget().getClass().getName();
        opLog.setClassName(className);
        opLog.setMethodName(method.getName());
        // 是否保存入参
        if (sysLog.paramSaved()) {
            String params = objectMapper.writeValueAsString(joinPoint.getArgs());
            opLog.setParams(params);
        }
        // 是否保存执行结果
        if (sysLog.saved() && result != null) {
            String resultStr = objectMapper.writeValueAsString(result);
            opLog.setResult(resultStr);
        }
        operationLogService.asyncSave(opLog);
    }

    private String resolveSpEL(String spELStr, Method method, Object[] args){
        int i = 0;
        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames != null) {
            for (String parameterName : parameterNames) {
                context.setVariable(parameterName, args[i ++]);
            }
        }
        if (spELStr.contains("#")) {
            return expressionParser.parseExpression(spELStr)
                    .getValue(context, String.class);
        }
        return  spELStr;
    }

}
