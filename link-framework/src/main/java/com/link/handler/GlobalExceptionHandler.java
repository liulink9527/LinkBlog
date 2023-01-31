package com.link.handler;

import com.link.domain.Result;
import com.link.enums.AppHttpCodeEnum;
import com.link.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Link
 * @Description
 * @date 2023-01-15 15:06
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public Result systemExceptionHandler(SystemException e) {

        //打印异常信息
        log.error("出现了异常！ {}", e);

        //从异常对象中获取提示信息封装返回
        return Result.errorResult(e.getCode(), e.getMsg());

    }

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(SystemException e) {

        //打印异常信息
        log.error("出现了异常！ {}", e);

        //从异常对象中获取提示信息封装返回
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());

    }

}
