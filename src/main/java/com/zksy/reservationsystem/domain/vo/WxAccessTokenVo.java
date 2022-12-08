package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信 access_token 请求值对象
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxAccessTokenVo {

    /**
     * 填写 client_credential
     */
    private final String grant_type = "client_credential";

    /**
     * 小程序唯一凭证，即 AppID，可在「微信公众平台 - 设置 - 开发设置」页中获得。（需要已经成为开发者，且帐号没有异常状态）
     */
    private String appid;

    /**
     * 小程序唯一凭证密钥，即 AppSecret，获取方式同 appid
     */
    private String secret;
}
