package com.zksy.reservationsystem.util.auth;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.domain.vo.*;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.util.constant.WechatConstant;
import com.zksy.reservationsystem.util.http.RestTemplateUtil;
import com.zksy.reservationsystem.util.time.WechatAccessTokenTimeTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 微信工具类
 *
 * @author kkkoke
 * @since 2022/12/8
 */
@Slf4j
@Component
public class WechatUtil {

    private static WechatConstant wechatConstant;

    public WechatUtil(WechatConstant wechatConstant) {
        WechatUtil.wechatConstant = wechatConstant;
    }

    private static final String AUTH_CODE_2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    private static final String SENT_NOTICE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

    public static String getWxOpenId(String code) {
        WechatAuthVo wechatAuthVo = createWechatAuthVo(code);
        String response = RestTemplateUtil.getHttp(AUTH_CODE_2SESSION_URL, JSONUtil.parseObj(wechatAuthVo));
        return handleResponseOfOpenId(response);
    }

    public static String getWxOpenId(String uname, String code) {
        WechatAuthVo wechatAuthVo = createWechatAuthVo(code);
        String response = RestTemplateUtil.getHttp(AUTH_CODE_2SESSION_URL, JSONUtil.parseObj(wechatAuthVo));
        return handleResponseOfOpenId(response);
    }

    public static String getAccessToken() {
        WxAccessTokenVo wxAccessTokenVo = createWxAccessTokenVo();
        String response = RestTemplateUtil.getHttp(ACCESS_TOKEN_URL, JSONUtil.parseObj(wxAccessTokenVo));
        return handleResponseOfAccessToken(response);
    }

    public static void sendNotice(String openId, Map<String, NoticeDataVo> dataMap) {
        String accessToken = WechatAccessTokenTimeTask.getAccessToken();
        WechatNoticeVo wechatNoticeVo = createWechatNoticeVo(openId, dataMap);
        String response = HttpUtil.createPost(SENT_NOTICE_URL + "?access_token=" + accessToken)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(wechatNoticeVo)).execute().body();
        handleResponseOfNotice(response);
    }

    private static WechatAuthVo createWechatAuthVo(String code) {
        return new WechatAuthVo(wechatConstant.getAppid(), wechatConstant.getSecret(), code);
    }

    private static WxAccessTokenVo createWxAccessTokenVo() {
        return new WxAccessTokenVo(wechatConstant.getAppid(), wechatConstant.getSecret());
    }

    private static WechatNoticeVo createWechatNoticeVo(String openId, Map<String, NoticeDataVo> dataMap) {
        return new WechatNoticeVo(wechatConstant.getTemplateId(), openId, dataMap,
                wechatConstant.getMiniprogramState(), wechatConstant.getLang());
    }

    private static String handleResponseOfOpenId(String response) {
        log.info(response);
        WechatAuthResultVo wechatAuthResultVo = JSONUtil.toBean(response, WechatAuthResultVo.class);
        int errcode = wechatAuthResultVo.getErrcode();
        if (Objects.equals(errcode, -1)) {
            log.error("系统繁忙，此时请开发者稍候再试");
            throw new BizException(ResultCode.FAILED, "微信登录授权出现异常");
        } else if (Objects.equals(errcode, 40029)) {
            log.error("code无效");
            throw new BizException(ResultCode.FAILED, "微信登录授权出现异常");
        } else if (Objects.equals(errcode, 45011)) {
            log.error("频率限制，每个用户每分钟100次");
            throw new BizException(ResultCode.FAILED, "微信登录授权出现异常");
        } else if (Objects.equals(errcode, 40226)) {
            log.error("高风险等级用户，小程序登录拦截");
            throw new BizException(ResultCode.FAILED, "微信登录授权出现异常");
        } else if (Objects.equals(errcode, 0)) {
            log.info("请求成功");
            return wechatAuthResultVo.getOpenid();
        } else {
            throw new BizException(ResultCode.FAILED, "出现未知异常");
        }
    }

    private static String handleResponseOfAccessToken(String response) {
        log.info(response);
        WxAccessTokenResultVo wxAccessTokenResultVo = JSONUtil.toBean(response, WxAccessTokenResultVo.class);
        return wxAccessTokenResultVo.getAccess_token();
    }

    private static void handleResponseOfNotice(String response) {
        log.info(response);
        WechatNoticeResultVo wechatNoticeResultVo = JSONUtil.toBean(response, WechatNoticeResultVo.class);
        int errcode = wechatNoticeResultVo.getErrcode();
        if (Objects.equals(errcode, 0)) {
            log.info("微信通知发送成功发送成功");
        } else if (Objects.equals(errcode, 40001)) {
            log.error("invalid credential  access_token is invalid or not latest");
            throw new BizException(ResultCode.FAILED, "微信通知发送异常");
        } else if (Objects.equals(errcode, 40003)) {
            log.error("invalid openid");
            throw new BizException(ResultCode.FAILED, "微信通知发送异常");
        } else if (Objects.equals(errcode, 40014)) {
            log.error("invalid access_token");
            throw new BizException(ResultCode.FAILED, "微信通知发送异常");
        } else if (Objects.equals(errcode, 40037)) {
            log.error("invalid template_id");
            throw new BizException(ResultCode.FAILED, "微信通知发送异常");
        }
    }
}
