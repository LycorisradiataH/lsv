package com.hdiata.common.exception;

import com.hdiata.common.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.hdiata.common.enums.StatusCodeEnum.FAIL;

/**
 * 业务异常
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/21 19:34
 */
@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(StatusCodeEnum statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

}
