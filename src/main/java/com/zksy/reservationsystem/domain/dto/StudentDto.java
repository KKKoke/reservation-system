package com.zksy.reservationsystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生信息请求返回类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    /** 编号 */
    private Integer id;

    /** 姓名 */
    private String name;

    /** 学号 */
    private String studentId;

    /** 联系方式 */
    private String contact;
}
