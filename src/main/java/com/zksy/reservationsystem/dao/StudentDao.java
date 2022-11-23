package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.dto.StudentDto;
import com.zksy.reservationsystem.domain.po.StudentPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生基础信息数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface StudentDao {

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
    Boolean insertStudent(StudentPo studentPo);

    /**
     * 删除学生
     */
    Boolean deleteStudent(String studentId);

    /**
     * 获取学生列表
     */
    List<StudentDto> queryStudentDtoList(String name, String studentId, String className, String dormitory);
}
