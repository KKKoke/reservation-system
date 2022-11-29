package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 老师值对象
 *
 * @author kkkoke
 * @since 2022/11/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVo {

    /** 姓名 */
    @NotBlank(message = "name can not be null")
    private String name;

    /** 工号 */
    @NotBlank(message = "jobId can not be null")
    private String jobId;

    /** 密码 */
    @NotBlank(message = "password can not be null")
    private String password;

    /** 联系方式 */
    @NotBlank(message = "contact can not be null")
    private String contact;

    /** 类型 */
    @NotNull(message = "type can not be null")
    private Integer type;

    /** 职位 */
    @NotBlank(message = "position can not be null")
    private String position;
}
