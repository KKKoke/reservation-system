package com.zksy.reservationsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.zksy.reservationsystem.common.CommonPage;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.TeaAuthDao;
import com.zksy.reservationsystem.dao.TeacherDao;
import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.TeacherService;
import com.zksy.reservationsystem.util.constant.TeacherConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

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
    public Boolean insertTeacher(String name, String jobId, String password, String contact, Integer type, String position) {
        if (!ObjectUtils.isEmpty(teacherDao.queryTeacherPoByJobId(jobId))) {
            throw new BizException(ResultCode.FAILED, "该老师已存在");
        }
        if (!Objects.equals(type, TeacherConstant.COMMON) && !Objects.equals(type, TeacherConstant.ADMIN)) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "错误的类型");
        }
        return teacherDao.insertTeacher(name, jobId, contact, type, position) &&
                teaAuthDao.insertTeaAuth(jobId, DigestUtils.md5DigestAsHex(password.getBytes()));
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
    public CommonPage<TeacherDto> queryTeacherDtoList(String name, String jobId, Integer type, Integer pageNum, Integer pageSize) {
        // 分页参数设置，pageNum 默认为1，pageSize 默认为10
        if (ObjectUtils.isEmpty(pageNum) || pageNum < 0 || pageNum >= 65535) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize) || pageSize < 0 || pageSize >= 65535) {
            pageSize = 10;
        }
        // 开启 pageHelper 自动分页插件
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherDto> teacherDtoList = teacherDao.queryTeacherDtoList(name, jobId, type);
        return CommonPage.restPage(teacherDtoList);
    }
}
