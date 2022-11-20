package com.zksy.reservationsystem.common;

/**
 * 常用API返回对象接口
 *
 * @author kkkoke
 * @since 2022/4/18
 */
public interface IError {
    /**
     * 返回码
     */
    String getResultCode();

    /**
     * 返回信息
     */
    String getResultMessage();
}
