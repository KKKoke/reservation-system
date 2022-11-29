package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.common.CommonPage;
import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.domain.vo.TeaUpdateVo;
import com.zksy.reservationsystem.domain.vo.TeacherVo;

/**
 * 老师服务层
 *
 * @author kkkoke
 * @since 2022/11/20
 */
public interface TeacherService {

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
    Boolean insertTeacher(TeacherVo teacherVo);

    /**
     * 删除老师
     */
    Boolean deleteTeacher(String jobId);

    /**
     * 获取老师列表
     */
    CommonPage<TeacherDto> queryTeacherDtoList(String name, String jobId, Integer type, Integer pageNum, Integer pageSize);

    /**
     * 更新老师信息
     */
    Boolean updateTeacher(TeaUpdateVo teaUpdateVo);
}
