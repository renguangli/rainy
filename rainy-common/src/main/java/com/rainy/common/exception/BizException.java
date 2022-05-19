package com.rainy.common.exception;

/**
 * 业务异常
 *
 * @author renguangli
 * @date 2022/4/13 11:30
 */
public class BizException extends RuntimeException{

    /** 业务编码 */
    private int code;

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
