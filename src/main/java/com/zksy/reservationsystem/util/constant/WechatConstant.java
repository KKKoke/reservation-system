package com.zksy.reservationsystem.util.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信常量类
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Component
public class WechatConstant {

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
     * 微信通知模版id
     */
    @Value("${wechat.template-id}")
    private String templateId;

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getTemplateId() {
        return templateId;
    }
}
