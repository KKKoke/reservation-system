package com.zksy.reservationsystem.common;

/**
 * 常用API返回对象
 *
 * @author kkkoke
 * @since 2022/4/18
 */
public enum ResultCode implements IError {
    SUCCESS("200", "操作成功"),

    FAILED("500", "操作失败"),
    VALIDATE_FAILED("400", "参数检验失败"),
    UNAUTHORIZED("401", "暂未登录或token已经过期"),
    FORBIDDEN("403", "没有相关权限");
    private final String resultCode;

    private final String resultMessage;

    ResultCode(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
