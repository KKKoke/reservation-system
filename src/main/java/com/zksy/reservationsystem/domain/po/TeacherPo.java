package com.zksy.reservationsystem.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 老师基础信息
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherPo {

    /** 编号 */
    private Integer id;

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

    /** 是否删除 */
    private Integer isDeleted;
}
