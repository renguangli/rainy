package com.rainy.core.config;

import cn.hutool.core.util.StrUtil;
import com.rainy.common.Result;
import com.rainy.common.constant.CharConstants;
import com.rainy.common.enums.ResultCode;
import com.rainy.common.exception.NotExistsException;
import com.rainy.common.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 *
 * @author renguangli
 * @date 2022/5/16 23:24
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截系统异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return Result.of(ResultCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 拦截认证失败异常
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result unauthorizedException(UnauthorizedException e,  HttpServletResponse response) {
        int status = ResultCode.UNAUTHORIZED.getCode();
        return Result.of(status, e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        int code = ResultCode.BAD_REQUEST.getCode();
        return Result.of(code,
                StrUtil.subBefore(e.getMessage(), CharConstants.COLON, false));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result illegalArgumentException(IllegalArgumentException e) {
        int code = ResultCode.BAD_REQUEST.getCode();
        return Result.of(code, e.getMessage());
    }

    @ExceptionHandler(value = NotExistsException.class)
    public Result notExistsException(NotExistsException e) {
        int code = ResultCode.BAD_REQUEST.getCode();
        return Result.of(code, e.getMessage());
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
     * 拦截参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Result bindException(BindException e) {
        return getResult(e.getBindingResult());
    }

    /**
     * 拦截参数校验异常
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
