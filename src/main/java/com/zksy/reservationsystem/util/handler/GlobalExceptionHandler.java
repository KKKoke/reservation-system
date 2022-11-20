package com.zksy.reservationsystem.util.handler;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理
 *
 * @author kkkoke
 * @since 2022/9/17
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<?> bizExceptionHandler(BizException e) {
        log.info("BizException.errorMsg:{}", e.getMessage());
        return CommonResult.failed(e.getErrorCode(), e.getMessage());
    }

    /**
     * 参数错误
     *
     * @param e 异常
     * @return ApiResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult<?> constraintViolationException(ConstraintViolationException e) {
        log.info("ConstraintViolationException.errMsg:{}", e.getMessage());
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, e.getMessage());
    }

    /**
     * 处理空指针的异常
     */
//    @ExceptionHandler(value = NullPointerException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public CommonResult<?> nullPointerExceptionHandler(NullPointerException e) {
//        log.error("NullPointerException.errorMsg:{}", e.getMessage());
//        return CommonResult.failed(ResultCode.FAILED, e.getMessage());
//    }


    /**
     * 处理其他异常
     */
//    @ExceptionHandler(value = Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public CommonResult<?> exceptionHandler(Exception e) {
//        log.error("Exception.errorMsg:{}", e.getMessage());
//        return CommonResult.failed(ResultCode.FAILED, e.getMessage());
//    }
}
