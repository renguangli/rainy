package com.rainy.common.exception;

/**
 * 不存在异常
 *
 * @author renguangli
 * @date 2022/3/30 10:47
 */
public class NotExistsException extends RuntimeException {


    /** Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotExistsException() {
        super();
    }

    public NotExistsException(String message) {
        super(message);
    }

    public NotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistsException(Throwable cause) {
        super(cause);
    }


}
