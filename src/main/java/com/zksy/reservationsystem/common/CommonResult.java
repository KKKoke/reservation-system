package com.zksy.reservationsystem.common;

import javax.validation.constraints.NotNull;

/**
 * 通用返回对象
 *
 * @author kkkoke
 * Created on 2022/5/9
 */
public class CommonResult<T> {

    /**
     * 状态码
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据封装
     */
    private T data;

    @NotNull
    protected CommonResult() {
    }

    protected CommonResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    @NotNull
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS.getResultCode(), ResultCode.SUCCESS.getResultMessage(), null);
    }

    /**
     * 成功返回结果
     */
    @NotNull
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getResultCode(), ResultCode.SUCCESS.getResultMessage(), data);
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(String resultMessage) {
        return new CommonResult<>(ResultCode.SUCCESS.getResultCode(), resultMessage, null);
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(T data, String resultMessage) {
        return new CommonResult<>(ResultCode.SUCCESS.getResultCode(), resultMessage, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(IError errorCode) {
        return new CommonResult<>(errorCode.getResultCode(), errorCode.getResultMessage(), null);
    }

    /**
     * 失败返回结果
     */
    @NotNull
    public static <T> CommonResult<T> failed(IError errorCode, String message) {
        return new CommonResult<>(errorCode.getResultCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(String resultMessage) {
        return new CommonResult<>(ResultCode.FAILED.getResultCode(), resultMessage, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(String resultCode, String resultMessage) {
        return new CommonResult<>(resultCode, resultMessage, null);
    }

    /**
     * 失败返回结果
     */
    @NotNull
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    @NotNull
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    @NotNull
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getResultCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getResultCode(), ResultCode.UNAUTHORIZED.getResultMessage(), null);
    }

    /**
     * 未登录返回结果
     */
    @NotNull
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getResultCode(), ResultCode.UNAUTHORIZED.getResultMessage(), data);
    }

    /**
     * 未登录返回结果
     */
    @NotNull
    public static <T> CommonResult<T> unauthorized(String message) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getResultCode(), message, null);
    }

    /**
     * 未授权返回结果
     */
    @NotNull
    public static <T> CommonResult<T> forbidden() {
        return new CommonResult<>(ResultCode.FORBIDDEN.getResultCode(), ResultCode.FORBIDDEN.getResultMessage(), null);
    }

    /**
     * 未授权返回结果
     */
    @NotNull
    public static <T> CommonResult<T> forbidden(String message) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getResultCode(), message, null);
    }

    /**
     * 未授权返回结果
     */
    @NotNull
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getResultCode(), ResultCode.FORBIDDEN.getResultMessage(), data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String resultMessage) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
