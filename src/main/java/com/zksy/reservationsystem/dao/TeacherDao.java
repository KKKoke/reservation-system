package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import org.apache.ibatis.annotations.Mapper;

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
}
