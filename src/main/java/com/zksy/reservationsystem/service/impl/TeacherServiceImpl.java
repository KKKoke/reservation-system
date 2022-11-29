package com.zksy.reservationsystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.zksy.reservationsystem.common.CommonPage;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.TeaAuthDao;
import com.zksy.reservationsystem.dao.TeacherDao;
import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.po.TeaAuthPo;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.domain.vo.TeaUpdateVo;
import com.zksy.reservationsystem.domain.vo.TeacherVo;
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
    public Boolean insertTeacher(TeacherVo teacherVo) {
        TeacherPo oldTeacherPo = teacherDao.queryTeacherPoWithDeleted(teacherVo.getJobId());
        TeaAuthPo oldTeaAuthPo = teaAuthDao.queryTeaAuthPoWithDeleted(teacherVo.getJobId());
        if (!Objects.equals(teacherVo.getType(), TeacherConstant.COMMON) && !Objects.equals(teacherVo.getType(), TeacherConstant.ADMIN)) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "错误的类型");
        }
        if (!ObjectUtils.isEmpty(oldTeacherPo)) {
            if (Objects.equals(oldTeacherPo.getIsDeleted(), 0)) {
                throw new BizException(ResultCode.FAILED, "该老师已存在");
            }
            TeacherPo teacherPoToUpdate = BeanUtil.copyProperties(teacherVo, TeacherPo.class);
            teacherPoToUpdate.setIsDeleted(0);
            if (!ObjectUtils.isEmpty(oldTeaAuthPo)) {
                return teacherDao.updateTeacherPo(teacherPoToUpdate) &&
                        teaAuthDao.updateTeaAuth(new TeaAuthPo(teacherVo.getJobId(),
                                DigestUtils.md5DigestAsHex(teacherVo.getPassword().getBytes()), 0));
            }
            return teacherDao.updateTeacherPo(teacherPoToUpdate) &&
                    teaAuthDao.insertTeaAuth(teacherVo.getJobId(), DigestUtils.md5DigestAsHex(teacherVo.getPassword().getBytes()));
        }
        return teacherDao.insertTeacher(teacherVo) &&
                teaAuthDao.insertTeaAuth(teacherVo.getJobId(), DigestUtils.md5DigestAsHex(teacherVo.getPassword().getBytes()));
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

    @Override
    public Boolean updateTeacher(TeaUpdateVo teaUpdateVo) {
        if (!Objects.equals(teaUpdateVo.getType(), TeacherConstant.COMMON) && !Objects.equals(teaUpdateVo.getType(), TeacherConstant.ADMIN)) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "错误的类型");
        }
        TeacherPo oldTeacherPo = teacherDao.queryTeacherPoByJobId(teaUpdateVo.getJobId());
        if (ObjectUtils.isEmpty(oldTeacherPo)) {
            throw new BizException(ResultCode.FAILED, "该老师不存在");
        }
        TeacherPo teacherPo = BeanUtil.copyProperties(teaUpdateVo, TeacherPo.class);
        teacherPo.setIsDeleted(0);
        return teacherDao.updateTeacherPo(teacherPo);
    }
}
