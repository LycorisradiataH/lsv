package com.hdiata.common.handler;

import com.hdiata.common.exception.ServiceException;
import com.hdiata.pojo.vo.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.hdiata.common.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.hdiata.common.enums.StatusCodeEnum.VALID_ERROR;

/**
 * 全局异常处理
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/21 19:31
 */
@Log4j2
@RestControllerAdvice
public class ControllerAdviceHandler {

    /**
     * 处理业务异常
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result<?> errorHandler(ServiceException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> errorHandler(MethodArgumentNotValidException e) {
        return Result.fail(VALID_ERROR.getCode(),
                Objects.requireNonNull(e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    /**
     * 处理系统异常
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> errorHandler(Exception e) {
        return Result.fail(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getMessage());
    }

}
