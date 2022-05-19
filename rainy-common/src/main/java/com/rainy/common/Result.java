package com.rainy.common;


import lombok.Data;

/**
 * fgc-common
 * Created by renguangli at 2021/10/18 9:30 上午
 *
 * @since JDK1.8
 */
@Data
public class Result {

    private int code;
    private boolean success;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(int code, boolean success, String message, Object data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static Result ok() {
        return new Result(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
    }

    public static Result of(ResultCode resultCode) {
        return new Result(resultCode.getCode(), false, resultCode.getMessage(), null);
    }

    public static Result ok(Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), data);
    }

    public static Result of(Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), data);
    }

    public static Result of(int code, String message) {
        return new Result(code, false, message, null);
    }

    public static Result unauthorized() {
        return new Result(ResultCode.UNAUTHORIZED.getCode(), false, ResultCode.UNAUTHORIZED.getMessage(), null);
    }

    public static Result unauthorized(String message) {
        return new Result(ResultCode.UNAUTHORIZED.getCode(), false, message, null);
    }

    public static Result forbidden(String message) {
        return new Result(ResultCode.FORBIDDEN.getCode(), false, message, null);
    }

}
