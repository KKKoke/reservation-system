package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 老师基础信息数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface TeacherDao {

    /**
     * 通过工号获取老师信息
     */
    TeacherDto queryTeacherDtoByJobId(String jobId);

    /**
     * 通过工号获取老师信息
     */
    TeacherPo queryTeacherPoByJobId(String jobId);

    /**
     * 新增老师
     */
    Boolean insertTeacher(String name, String jobId, String contact);

    /**
     * 删除老师
     */
    Boolean deleteTeacher(String jobId);

    /**
     * 获取老师列表
     */
    List<TeacherDto> queryTeacherDtoList(String name, String jobId);
}
