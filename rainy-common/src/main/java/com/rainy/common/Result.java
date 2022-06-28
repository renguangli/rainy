package com.rainy.common;


import com.rainy.common.enums.ResultCode;
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

    public static Result ok(Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), data);
    }

    public static Result of(ResultCode resultCode) {
        return new Result(resultCode.getCode(), false, resultCode.getMessage(), null);
    }

    public static Result of(ResultCode resultCode, String message) {
        return new Result(resultCode.getCode(), false, message, null);
    }

}
