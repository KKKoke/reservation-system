package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信登录值对象
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class WechatAuthVo {

    /**
     * 小程序 appid
     */
    @Value("${wechat.app-id}")
    private String appid;

    /**
     * 小程序 appSecret
     */
    @Value("${wechat.app-secret}")
    private String secret;

    /**
     * 登录时获取的code
     */
    private String js_code;

    /**
     * 授权类型，此处只需填写 authorization_code
     */
    private final String grant_type = "authorization_code";

    public WechatAuthVo(String js_code) {
        this.js_code = js_code;
    }
}
