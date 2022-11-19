package com.zksy.reservationsystem.exception;


import com.zksy.reservationsystem.common.IError;

/**
 * 自定义异常类
 *
 * @author kkkoke
 * Created on 2022/9/17
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public BizException() {
        super();
    }

    public BizException(IError iErrorCode) {
        super(iErrorCode.getResultMessage());
        this.errorCode = iErrorCode.getResultCode();
        this.errorMsg = iErrorCode.getResultMessage();
    }

    public BizException(IError iErrorCode, Throwable cause) {
        super(iErrorCode.getResultMessage(), cause);
        this.errorCode = iErrorCode.getResultCode();
        this.errorMsg = iErrorCode.getResultMessage();
    }

    public BizException(IError iErrorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = iErrorCode.getResultCode();
        this.errorMsg = errorMsg;
    }

    public BizException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(String errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
