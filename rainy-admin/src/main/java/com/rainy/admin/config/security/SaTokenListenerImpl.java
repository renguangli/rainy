package com.rainy.admin.config.security;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.util.SaFoxUtil;
import com.rainy.common.enums.LoginType;
import com.rainy.core.entity.LoginLog;
import com.rainy.core.entity.User;
import com.rainy.core.service.LoginLogService;
import com.rainy.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/15 17:10
 */
@Slf4j
@Component
public class SaTokenListenerImpl implements SaTokenListener {

    @Resource
    private UserService userService;
    @Resource
    private LoginLogService loginLogService;

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        println("账号[" + loginId + "]登录成功");
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        saveLog(loginId, LoginType.LOGOUT);
        println("账号[" + loginId + "]注销成功 (Token=" + tokenValue + ")");
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        saveLog(loginId, LoginType.KICK_OUT);
        println("账号[" + loginId + "]被踢下线 (Token=" + tokenValue + ")");
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        saveLog(loginId, LoginType.REPLACED);
        println("账号[" + loginId + "]被顶下线 (Token=" + tokenValue + ")");
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, long disableTime) {
        saveLog(loginId, LoginType.DISABLE);
        Date date = new Date(System.currentTimeMillis() + disableTime * 1000);
        println("账号[" + loginId + "]被封禁 (解封时间: " + SaFoxUtil.formatDate(date) + ")");
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId) {
        saveLog(loginId, LoginType.UNTIE_DISABLE);
        println("账号[" + loginId + "]被解除封禁");
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
//        println("Session[" + id + "]创建成功");
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
//        println("Session[" + id + "]注销成功");
    }

    private void saveLog(Object loginId, LoginType loginType) {
        User user = userService.getById(loginId.toString());
        LoginLog loginLog = new LoginLog();
        loginLog.setDatetime(LocalDateTime.now());
        loginLog.setLoginType(loginType.getCode());
        loginLog.setUsername(user.getUsername());
        loginLog.setIp(user.getLastLoginIp());
        loginLog.setBrowser(user.getBrowser());
        loginLog.setSuccess(true);
        loginLog.setOs(user.getOs());
        loginLogService.asyncSave(loginLog);
    }

    /**
     * 日志输出的前缀
     */
    public static final String LOG_PREFIX = "Login-Monitor-Log -->: ";

    /**
     * 打印指定字符串
     * @param str 字符串
     */
    public void println(String str) {
        if (SaManager.getConfig().getIsLog()) {
            System.out.println(LOG_PREFIX + str);
        }
    }

}
