package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.TeaAuthDao;
import com.zksy.reservationsystem.dao.TeacherDao;
import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

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

    private final TeaAuthDao teaAuthDao;

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

    @Override
    @Transactional
    public Boolean insertTeacher(String name, String jobId, String password, String contact) {
        if (!ObjectUtils.isEmpty(teacherDao.queryTeacherPoByJobId(jobId))) {
            throw new BizException(ResultCode.FAILED, "该老师已存在");
        }
        return teacherDao.insertTeacher(name, jobId, contact) && teaAuthDao.insertTeaAuth(jobId, DigestUtils.md5DigestAsHex(password.getBytes()));
    }

    @Override
    @Transactional
    public Boolean deleteTeacher(String jobId) {
        if (ObjectUtils.isEmpty(teacherDao.queryTeacherPoByJobId(jobId))) {
            throw new BizException(ResultCode.FAILED, "该老师不存在");
        }
        return teacherDao.deleteTeacher(jobId) && teaAuthDao.deleteTeaAuth(jobId);
    }

    @Override
    public List<TeacherDto> queryTeacherDtoList() {
        return teacherDao.queryTeacherDtoList();
    }
}
