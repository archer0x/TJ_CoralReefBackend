package org.example.coralreef_backend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一接口返回结果类
 */
@Data
public class Result<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 通用成功返回方法
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 通用失败返回方法（无数据体）
     */
    public static <T> Result<T> failure() {
        return new Result<>(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg(), null);
    }

    /**
     * 通用失败返回方法（包含数据体）
     */
    public static <T> Result<T> failure(T data) {
        return new Result<>(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg(), data);
    }

    /**
     * 自定义失败返回方法（无数据体）
     */
    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 自定义失败返回方法（包含数据体）
     */
    public static <T> Result<T> failure(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    /**
     * 使用错误枚举返回失败（无数据体）
     */
    public static <T> Result<T> failure(CodeEnum codeEnum) {
        return new Result<>(codeEnum.getCode(), codeEnum.getMsg(), null);
    }

    /**
     * 使用错误枚举返回失败（包含数据体）
     */
    public static <T> Result<T> failure(CodeEnum codeEnum, T data) {
        return new Result<>(codeEnum.getCode(), codeEnum.getMsg(), data);
    }

}
