package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.po.TeacherPo;

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
}
