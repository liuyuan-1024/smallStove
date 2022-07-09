package com.bug.framework.exception.handler;

import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器(处理控制层的异常)
 * 注解：@RestControllerAdvice == @ControllerAdvice + @ResponseBody
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理系统异常
     */
    @ExceptionHandler(SystemException.class)
    public Result<?> handleSystemException(SystemException e) {
        logger.error("系统异常, {e}", e);
        // 封装异常信息并返回
        return new ResultBuilder().error(e.getResultEnum());
    }

    /**
     * 处理认证失败异常
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result<?> handleBadCredentialsException(BadCredentialsException e) {
        logger.error("认证失败异常, {e}", e);
        // 封装异常信息并返回
        return new ResultBuilder().error(ResultEnum.LOGIN_FAIL);
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        logger.error("全局异常, {e}", e);
        // 封装异常信息并返回
        return new ResultBuilder().error(ResultEnum.GLOBAL_EXCEPTION, e.getMessage());
    }
}
