package com.rainy.common.constant;

/**
 * 配置编码常量类
 *
 * @author renguangli
 * @date 2022/4/26 15:27
 */
public interface ConfigConstants {

    /** 是否是演示环境 */
    String IS_DEMO_DEV = "IS_DEMO_DEV";

    /** 系统名称 */
    String SYS_TITLE = "SYS_TITLE";
    /**  系统 logo */
    String SYS_LOGO = "SYS_LOGO";
    /** 是否开启验证码 */
    String CAPTCHA_ENABLE = "CAPTCHA_ENABLE";
    /** 头像上传路径 */
    String AVATAR_UPLOAD_PATH = "AVATAR_UPLOAD_PATH";
    /** 重置密码 */
    String RESET_PASSWORD = "RESET_PASSWORD";

    /** 临时token有效期 */
    String TEMP_TOKEN_TIMEOUT = "TEMP_TOKEN_TIMEOUT";

    /** mail */
    String MAIL_CONFIG = "MAIL_CONFIG";
    /** 账号激活邮件标题 */
    String ACTIVATE_ACCOUNT_MAIL_TITLE = "ACTIVATE_ACCOUNT_MAIL_TITLE";
    /** 账号激活邮件内容 */
    String ACTIVATE_ACCOUNT_MAIL_CONTENT = "ACTIVATE_ACCOUNT_MAIL_CONTENT";
    /** 账号激活地址 */
    String ACTIVATE_ACCOUNT_ADDR = "ACTIVATE_ACCOUNT_ADDR";

    /** 是否保存查询操作日志 */
    String QUERY_LOG_SAVED = "QUERY_LOG_SAVED";
    /** 日志保留天数 */
    String LOG_RETENTION_DAYS = "LOG_RETENTION_DAYS";

}