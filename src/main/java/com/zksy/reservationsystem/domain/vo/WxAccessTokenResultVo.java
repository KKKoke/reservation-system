package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信 access_token 请求返回值对象
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxAccessTokenResultVo {

    /**
     * 获取到的凭证
     */
    private String access_token;

    /**
     * 凭证有效时间，单位：秒。目前是7200秒之内的值。
     */
    private Integer expires_in;
}
