package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限认证值对象
 *
 * @author kkkoke
 * @since 2022/9/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthVo {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;
}
