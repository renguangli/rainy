package com.rainy.core.aop;

import com.rainy.common.dto.LoginDTO;
import com.rainy.common.enums.LoginType;
import com.rainy.common.util.WebUtils;
import com.rainy.sys.entity.LoginLog;
import com.rainy.sys.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

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
@RequiredArgsConstructor
public class LoginLogAspect {

    // 登录日志切入点
    private static final String LOGIN_LOG_POINTCUT = "execution(public * com.rainy.controller.SsoServerController.login(..))";

    private final LoginLogService loginLogService;

    /**
     * 打印方法执行时常
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     * @throws Throwable Throwable
     */
    @Around(LOGIN_LOG_POINTCUT)
    public Object printMethodExecuteTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        LoginLog loginLog = new LoginLog();
        String username = ((LoginDTO) args[0]).getUsername();
        loginLog.setUsername(username);
        loginLog.setLoginType(LoginType.LOGIN.getCode());
        loginLog.setDatetime(LocalDateTime.now());
        loginLog.setIp(WebUtils.getRemoteIp());
        loginLog.setBrowser(WebUtils.getBrowser());
        loginLog.setOs(WebUtils.getOs());
        loginLog.setSuccess(true);
        try {
            return joinPoint.proceed(args);
        } catch (Throwable e) {
            loginLog.setSuccess(false);
            loginLog.setErrorMessage(e.getMessage());
            throw e;
        } finally {
            loginLogService.asyncSave(loginLog);
        }
    }

}
