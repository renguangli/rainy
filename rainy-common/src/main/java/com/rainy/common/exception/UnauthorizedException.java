package com.rainy.common.exception;

import com.rainy.common.ResultCode;

/**
 * 未认证异常/认证失败异常
 *
 * @author renguangli
 * @date 2022/5/16 23:24
 */
public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(ResultCode resultCode) {
        super(resultCode.getMessage());
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable e) {
        super(message, e);
    }


}
