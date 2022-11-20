package com.zksy.reservationsystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 老师信息请求返回类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {

    /** 编号 */
    private Integer id;

    /** 姓名 */
    private String name;

    /** 工号 */
    private String jobId;

    /** 联系方式 */
    private String contact;

    /** 类型  1：老师，2：管理员 */
    private Integer type;
}
