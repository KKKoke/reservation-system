package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.common.CommonPage;
import com.zksy.reservationsystem.domain.dto.StudentDto;
import com.zksy.reservationsystem.domain.po.StudentPo;
import com.zksy.reservationsystem.domain.vo.StuUpdateVo;
import com.zksy.reservationsystem.domain.vo.StudentVo;

import java.util.List;

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

    /**
     * 新增学生
     */
    Boolean insertStudent(StudentVo studentVo);

    /**
     * 删除学生
     */
    Boolean deleteStudent(String studentId);

    /**
     * 获取学生列表
     */
    CommonPage<StudentDto> queryStudentDtoList(String name, String studentId, String className, String dormitory, Integer pageNum, Integer pageSize);

    /**
     * 更新学生信息
     */
    Boolean updateStudent(StuUpdateVo stuUpdateVo);
}
