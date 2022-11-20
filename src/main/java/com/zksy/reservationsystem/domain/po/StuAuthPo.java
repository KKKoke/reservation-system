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

    /** 编号 */
    private Integer id;

    /** 账号 */
    private String uname;

    /** 密码 */
    private String passwd;

    /** 是否删除 */
    private Integer isDeleted;
}
