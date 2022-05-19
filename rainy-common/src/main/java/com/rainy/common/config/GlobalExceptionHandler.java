package com.rainy.common.config;

import cn.hutool.core.util.StrUtil;
import com.rainy.common.CharConstants;
import com.rainy.common.Result;
import com.rainy.common.ResultCode;
import com.rainy.common.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 *
 * @author renguangli
 * @date 2022/5/16 23:24
 */
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e) {
        log.error(e.getMessage(), e);
        return Result.of(ResultCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 认证失败异常
     * @param e UnauthorizedException
     * @return Result
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result unauthorizedException(UnauthorizedException e) {
        return Result.of(ResultCode.UNAUTHORIZED.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Result.of(ResultCode.BAD_REQUEST.getCode(),
                StrUtil.subBefore(e.getMessage(), CharConstants.COLON, false));
    }

    /**
     * 请求方法不支持
     * @param e HttpRequestMethodNotSupportedException
     * @return Result
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.of(ResultCode.METHOD_NOT_ALLOW.getCode(), e.getMessage());
    }

    /**
     * 参数绑定异常
     * @param e BindException
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    public Result bindException(BindException e) {
        return getResult(e.getBindingResult());
    }

    /**
     * 参数绑定异常
     * @param e BindException
     * @return Result
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return getResult(e.getBindingResult());
    }

    private Result getResult(BindingResult result) {
        String message = null;
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return Result.of(ResultCode.BAD_REQUEST.getCode(), message);
    }

}
