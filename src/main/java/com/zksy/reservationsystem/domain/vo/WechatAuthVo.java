package com.zksy.reservationsystem.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信登录值对象
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Data
@NoArgsConstructor
public class WechatAuthVo {

    /**
     * 小程序 appid
     */
    private String appid;

    /**
     * 小程序 appSecret
     */
    private String secret;

    /**
     * 登录时获取的code
     */
    private String js_code;

    /**
     * 授权类型，此处只需填写 authorization_code
     */
    private final String grant_type = "authorization_code";

    public WechatAuthVo(String appid, String secret, String js_code) {
        this.appid = appid;
        this.secret = secret;
        this.js_code = js_code;
    }
}
