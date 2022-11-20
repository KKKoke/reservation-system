package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.domain.dto.StudentDto;
import com.zksy.reservationsystem.domain.po.StudentPo;

/**
 * 学生服务层
 *
 * @author kkkoke
 * @since 2022/11/20
 */
public interface StudentService {

    /**
     * 通过学号获取学生信息
     */
    StudentDto queryStudentDtoByStudentId(String studentId);

    /**
     * 通过学号获取学生信息
     */
    StudentPo queryStudentPoByStudentId(String studentId);
}
