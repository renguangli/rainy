package com.rainy.common.enums;

/**
 *
 * Created by renguangli at 2021/10/18 10:08 上午
 * @author renguangli
 * @since JDK1.8
 */
public enum ResultCode {

    /** success */
    SUCCESS(200, "success"),
    BAD_REQUEST(400, "Bad Request."),

    UNAUTHORIZED(401, "未认证！"),
    ACCOUNT_NOT_ACTIVATE(401, "账号未激活!"),
    ACCOUNT_EXPIRED(401, "账号已过期！"),
    PASSWORD_EXPIRED(401, "密码已过期!"),
    ACCOUNT_PASSWORD_NOT_MATCH(401, "请输入正确的用户名与密码!"),

    UNAUTHORIZED2(402, "Secondary authentication failed."),
    FORBIDDEN(403, "Forbidden."),
    NOT_FOUND(404, "Not Found."),
    METHOD_NOT_ALLOW(405, "Request method not supported."),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error.");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
