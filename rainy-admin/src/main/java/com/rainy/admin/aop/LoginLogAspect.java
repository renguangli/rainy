package com.rainy.admin.aop;

import com.rainy.admin.dto.LoginDTO;
import com.rainy.admin.util.WebUtils;
import com.rainy.common.LoginType;
import com.rainy.core.entity.LoginLog;
import com.rainy.core.service.LoginLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.core.task.AsyncTaskExecutor;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/14 16:36
 */
//@Aspect
//@Component
//@Slf4j
public class LoginLogAspect {

    // 登录日志切入点
    private static final String LOGIN_LOG_POINTCUT = "execution(public * com.rainy.admin.controller.SsoServerController.*(..))";

    @Resource
    private AsyncTaskExecutor asyncTaskExecutor;
    @Resource
    private LoginLogService loginLogService;

    /**
     * 打印方法执行时常
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     * @throws Throwable Throwable
     */
    @Around(LOGIN_LOG_POINTCUT)
    public Object printMethodExecuteTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        LoginLog loginLog = new LoginLog();
        String username = "Unknown";
        Object[] args = joinPoint.getArgs();
        int loginType = LoginType.LOGOUT;
        if ("login".equals(methodName)) {
            username = ((LoginDTO) args[0]).getUsername();
            loginType = LoginType.LOGIN;
        } else if ("logout".equals(methodName)) {
            username = WebUtils.getUsername();
        }
        loginLog.setUsername(username);
        loginLog.setLoginType(loginType);
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
            asyncTaskExecutor.execute(() -> loginLogService.save(loginLog));
        }
    }

}
