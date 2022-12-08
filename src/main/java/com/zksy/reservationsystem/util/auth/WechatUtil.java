package com.zksy.reservationsystem.util.auth;

import cn.hutool.json.JSONUtil;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.domain.vo.WechatAuthResultVo;
import com.zksy.reservationsystem.domain.vo.WechatAuthVo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.util.constant.WechatConstant;
import com.zksy.reservationsystem.util.http.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    public static String getWxOpenId(String code) {
        WechatAuthVo wechatAuthVo = createWechatAuthVo(code);
        String response = RestTemplateUtil.getHttp(AUTH_CODE_2SESSION_URL, JSONUtil.parseObj(wechatAuthVo));
        return handleResponse(response);
    }

    public static String getWxOpenId(String uname, String code) {
        WechatAuthVo wechatAuthVo = createWechatAuthVo(code);
        String response = RestTemplateUtil.getHttp(AUTH_CODE_2SESSION_URL, JSONUtil.parseObj(wechatAuthVo));
        return handleResponse(response);
    }

    private static WechatAuthVo createWechatAuthVo(String code) {
        return new WechatAuthVo(wechatConstant.getAppid(), wechatConstant.getSecret(), code);
    }

    private static String handleResponse(String response) {
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
}
