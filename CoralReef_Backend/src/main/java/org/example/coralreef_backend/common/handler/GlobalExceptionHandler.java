package org.example.coralreef_backend.common.handler;

import jakarta.servlet.http.HttpServletResponse;
import org.example.coralreef_backend.common.CodeEnum;
import org.example.coralreef_backend.common.Result;
import org.example.coralreef_backend.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger ERRORLOGGER = LoggerFactory.getLogger("errorLog");

    @ExceptionHandler(RuntimeException.class)
    public Result<?> unDefinedExceptionHandler(RuntimeException e , HttpServletResponse response){
        ERRORLOGGER.error("运行时异常 ", e);
        return Result.failure(CodeEnum.SYSTEM_ERROR.getCode(),CodeEnum.SYSTEM_ERROR.getMsg());
    }

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception e ,HttpServletResponse response){
        ERRORLOGGER.error("非运行时异常 ",e);
        return Result.failure(CodeEnum.SYSTEM_ERROR.getCode(),CodeEnum.SYSTEM_ERROR.getMsg());
    }

    @ExceptionHandler(CustomException.class)
    public Result<String> customException(CustomException e){
        ERRORLOGGER.error("自定义异常， code: {}, msg: {}, desc: {}",
                e.getCode(), e.getMsg(),e.getDesc());
        return Result.failure(e.getCode(), e.getMsg());
    }

}
