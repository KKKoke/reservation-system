package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信通知请求返回类
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatNoticeResultVo {

    /**
     * 错误码
     */
    private int errcode;

    /**
     * 错误信息
     */
    private String errmsg;
}
