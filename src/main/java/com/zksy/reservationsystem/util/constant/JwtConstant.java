package com.zksy.reservationsystem.util.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Jwt 常量类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Component
public class JwtConstant {

    @Value("${jwt.tokenHead}")
    public String TOKEN_HEAD;

    @Value("${jwt.tokenHeader}")
    public String TOKEN_HEADER;

    /**
     * 学生登录类型
     */
    public static final Integer STU_LOGIN_TYPE = 1;

    /**
     * 老师登录类型
     */
    public static final Integer TEA_LOGIN_TYPE = 2;
}
