package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.TeacherDao;
import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 老师服务层
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDao teacherDao;

    @Override
    public TeacherDto queryTeacherDtoByJobId(String jobId) {
        TeacherDto teacherDto = teacherDao.queryTeacherDtoByJobId(jobId);
        if (ObjectUtils.isEmpty(teacherDto)) {
            throw new BizException(ResultCode.FAILED, "该老师不存在");
        }
        return teacherDto;
    }

    @Override
    public TeacherPo queryTeacherPoByJobId(String jobId) {
        TeacherPo teacherPo = teacherDao.queryTeacherPoByJobId(jobId);
        if (ObjectUtils.isEmpty(teacherPo)) {
            throw new BizException(ResultCode.FAILED, "该老师不存在");
        }
        return teacherPo;
    }
}
