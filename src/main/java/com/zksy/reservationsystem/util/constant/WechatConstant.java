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

    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    @Value("${wechat.miniprogram-state}")
    private String miniprogramState;

    /**
     * 进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN
     */
    @Value("${wechat.lang}")
    private String lang;

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getMiniprogramState() {
        return miniprogramState;
    }

    public String getLang() {
        return lang;
    }
}
