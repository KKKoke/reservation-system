package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 学生值对象
 *
 * @author kkkoke
 * @since 2022/11/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVo {

    /**
     * 姓名
     */
    @NotBlank(message = "name can not be null")
    private String name;

    /**
     * 学号
     */
    @NotBlank(message = "studentId can not be null")
    private String studentId;

    /**
     * 密码
     */
    @NotBlank(message = "password can not be null")
    private String password;

    /**
     * 班级
     */
    @NotBlank(message = "className can not be null")
    private String className;

    /**
     * 性别
     */
    @NotBlank(message = "gender can not be null")
    private String gender;

    /**
     * 父母联系方式
     */
    @NotBlank(message = "parentContact can not be null")
    private String parentContact;

    /**
     * 联系方式
     */
    @NotBlank(message = "contact can not be null")
    private String contact;

    /**
     * 寝室号
     */
    @NotBlank(message = "dormitory can not be null")
    private String dormitory;
}
