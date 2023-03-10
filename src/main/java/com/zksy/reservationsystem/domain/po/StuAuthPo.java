package com.zksy.reservationsystem.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生认证类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuAuthPo {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 账号
     */
    private String uname;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 是否绑定
     */
    private Integer isBound;

    /**
     * 微信 openid
     */
    private String wxOpenId;

    public StuAuthPo(String uname, String passwd, Integer isDeleted) {
        this.uname = uname;
        this.passwd = passwd;
        this.isDeleted = isDeleted;
    }
}
