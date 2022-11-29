package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 老师信息更新值对象
 *
 * @author kkkoke
 * @since 2022/11/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaUpdateVo {

    /** 姓名 */
    private String name;

    /** 工号 */
    private String jobId;

    /** 职位 */
    private String position;

    /** 联系方式 */
    private String contact;

    /** 类型  1：老师，2：管理员 */
    private Integer type;
}
