package com.hdiata.pojo.vo;

import com.hdiata.common.enums.StatusCodeEnum;
import lombok.Data;

import static com.hdiata.common.enums.StatusCodeEnum.*;

/**
 * 接口返回类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 17:28
 */
@Data
public class Result<T> {

    /**
     * 返回状态
     */
    private Boolean status;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static <T> Result<T> success() {
        return restResult(true, null, SUCCESS.getCode(), SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data) {
        return restResult(true, data, SUCCESS.getCode(), SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data, String message) {
        return restResult(true, data, SUCCESS.getCode(), message);
    }

    public static <T> Result<T> fail() {
        return restResult(false, null, FAIL.getCode(), FAIL.getMessage());
    }

    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum) {
        return restResult(false, null, statusCodeEnum.getCode(), statusCodeEnum.getMessage());
    }

    public static <T> Result<T> fail(String message) {
        return restResult(false, message);
    }

    public static <T> Result<T> fail(T data) {
        return restResult(false, data, FAIL.getCode(), FAIL.getMessage());
    }

    public static <T> Result<T> fail(T data, String message) {
        return restResult(false, data, FAIL.getCode(), message);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return restResult(false, null, code, message);
    }

    private static <T> Result<T> restResult(Boolean status, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setStatus(status);
        apiResult.setCode(status ? SUCCESS.getCode() : FAIL.getCode());
        apiResult.setMessage(message);
        return apiResult;
    }

    private static <T> Result<T> restResult(Boolean status, T data, Integer code, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setStatus(status);
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }

}
