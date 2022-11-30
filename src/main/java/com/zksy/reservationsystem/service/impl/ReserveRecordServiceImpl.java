package com.zksy.reservationsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.zksy.reservationsystem.common.CommonPage;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.*;
import com.zksy.reservationsystem.domain.dto.ReserveRecordDto;
import com.zksy.reservationsystem.domain.po.*;
import com.zksy.reservationsystem.domain.vo.RecordSearchVo;
import com.zksy.reservationsystem.domain.vo.ReserveRecordVo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.ReserveRecordService;
import com.zksy.reservationsystem.util.common.BeanConvertor;
import com.zksy.reservationsystem.util.constant.ReserveConstant;
import com.zksy.reservationsystem.util.holder.StudentPoHolder;
import com.zksy.reservationsystem.util.holder.TeacherPoHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 常用时间段服务层实现类
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Service
@RequiredArgsConstructor
public class ReserveRecordServiceImpl implements ReserveRecordService {

    private final StudentDao studentDao;

    private final TeacherDao teacherDao;

    private final PeriodDao periodDao;

    private final ReserveTypeDao reserveTypeDao;

    private final ReserveRecordDao reserveRecordDao;

    private final ReentrantLock lock;

    @Override
    @Transactional
    public Boolean insertReserveRecord(ReserveRecordVo reserveRecordVo) {
        if (ObjectUtils.isEmpty(studentDao.queryStudentPoByStudentId(reserveRecordVo.getStudentId()))) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该学生不存在");
        }
        if (ObjectUtils.isEmpty(teacherDao.queryTeacherPoByJobId(reserveRecordVo.getJobId()))) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该老师不存在");
        }
        if (!isReserveTypeJsonStrValid(reserveRecordVo.getReserveType())) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该访谈类型不存在");
        }
        try {
            if (lock.tryLock()) {
                PeriodPo periodPo = periodDao.queryPeriodPoByPeriodId(reserveRecordVo.getPeriodId());
                if (ObjectUtils.isEmpty(periodPo)) {
                    throw new BizException(ResultCode.VALIDATE_FAILED, "该时间段不存在");
                }
                if (Objects.equals(periodPo.getIsReserved(), 1)) {
                    throw new BizException(ResultCode.FAILED, "该时间段已被人预约，请预约别的时间段");
                }
                if (!Objects.equals(periodPo.getJobId(), reserveRecordVo.getJobId())) {
                    throw new BizException(ResultCode.VALIDATE_FAILED, "不能预约别的老师的时间段");
                }
                return periodDao.updateIsReservedAndStudentId(reserveRecordVo.getPeriodId(), 1, reserveRecordVo.getStudentId())
                        && reserveRecordDao.insertReserveRecord(reserveRecordVo, periodPo.getStartTime(), periodPo.getEndTime(), reserveRecordVo.getPeriodId());
            } else {
                throw new BizException(ResultCode.FAILED, "当前时间段预约人数过多，请重试");
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public CommonPage<ReserveRecordDto> queryReserveRecordDtoListByJobId(String jobId, Integer pageNum, Integer pageSize) {
        if (ObjectUtils.isEmpty(teacherDao.queryTeacherPoByJobId(jobId))) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该老师不存在");
        }
        // 分页参数设置，pageNum 默认为1，pageSize 默认为10
        if (ObjectUtils.isEmpty(pageNum) || pageNum < 0 || pageNum >= 65535) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize) || pageSize < 0 || pageSize >= 65535) {
            pageSize = 10;
        }
        // 开启 pageHelper 自动分页插件
        PageHelper.startPage(pageNum, pageSize);
        List<ReserveRecordPo> reserveRecordPoList = reserveRecordDao.queryReserveRecordPoListByJobId(jobId);
        CommonPage<ReserveRecordPo> oldCommonPage = CommonPage.restPage(reserveRecordPoList);
        List<ReserveRecordDto> reserveRecordDtoList = BeanConvertor.reserveRecordPoListToDtoList(reserveRecordPoList);
        assert reserveRecordDtoList != null;
        return CommonPage.setNewList(oldCommonPage, reserveRecordDtoList);
    }

    @Override
    public CommonPage<ReserveRecordDto> queryReserveRecordDtoListByStudentId(String studentId, Integer pageNum, Integer pageSize) {
        if (ObjectUtils.isEmpty(studentDao.queryStudentPoByStudentId(studentId))) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该学生不存在");
        }
        // 分页参数设置，pageNum 默认为1，pageSize 默认为10
        if (ObjectUtils.isEmpty(pageNum) || pageNum < 0 || pageNum >= 65535) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize) || pageSize < 0 || pageSize >= 65535) {
            pageSize = 10;
        }
        // 开启 pageHelper 自动分页插件
        PageHelper.startPage(pageNum, pageSize);
        List<ReserveRecordPo> reserveRecordPoList = reserveRecordDao.queryReserveRecordPoListByStudentId(studentId);
        CommonPage<ReserveRecordPo> oldCommonPage = CommonPage.restPage(reserveRecordPoList);
        List<ReserveRecordDto> reserveRecordDtoList = BeanConvertor.reserveRecordPoListToDtoList(reserveRecordPoList);
        assert reserveRecordDtoList != null;
        return CommonPage.setNewList(oldCommonPage, reserveRecordDtoList);
    }

    @Override
    @Transactional
    public Boolean checkReserveRecord(Integer recordId, Integer status, String rejectReason) {
        ReserveRecordPo reserveRecordPo = reserveRecordDao.queryReserveRecordPoByRecordId(recordId);
        if (ObjectUtils.isEmpty(reserveRecordPo)) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该访谈记录不存在");
        }
        if (!Objects.equals(reserveRecordPo.getJobId(), TeacherPoHolder.getTeacherPo().getJobId())) {
            throw new BizException(ResultCode.FAILED, "不能审核其他老师的访谈预约");
        }
        // 审核通过
        if (Objects.equals(status, ReserveConstant.PASSED)) {
            return reserveRecordDao.updateStatus(recordId, status);
        }
        // 审核不通过，需要说明拒绝原因
        else if (Objects.equals(status, ReserveConstant.NOT_PASSED)) {
            if (ObjectUtils.isEmpty(rejectReason)) {
                throw new BizException(ResultCode.VALIDATE_FAILED, "请说明拒绝理由");
            }
            return reserveRecordDao.updateStatus(recordId, status)
                    && reserveRecordDao.updateRejectReason(recordId, rejectReason)
                    && periodDao.updateIsReservedAndStudentId(reserveRecordPo.getPeriodId(), 0, null);
        } else {
            throw new BizException(ResultCode.VALIDATE_FAILED, "不合法的状态值");
        }
    }

    @Override
    @Transactional
    public Boolean submitFeedback(Integer recordId, String feedback) {
        ReserveRecordPo reserveRecordPo = reserveRecordDao.queryReserveRecordPoByRecordId(recordId);
        if (ObjectUtils.isEmpty(reserveRecordPo)) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该访谈记录不存在");
        }
        if (!Objects.equals(reserveRecordPo.getStatus(), 2) && !Objects.equals(reserveRecordPo.getStatus(), 4)) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "待审核、被拒绝或者取消的访谈不能填写反馈");
        }
        TeacherPo teacherPo = TeacherPoHolder.getTeacherPo();
        StudentPo studentPo = StudentPoHolder.getStudentPo();
        if (!ObjectUtils.isEmpty(teacherPo) && Objects.equals(teacherPo.getJobId(), reserveRecordPo.getJobId())) {
            return reserveRecordDao.updateTeaFeedback(recordId, feedback)
                    && reserveRecordDao.updateStatus(recordId, ReserveConstant.ENDED);
        } else if (!ObjectUtils.isEmpty(studentPo) && Objects.equals(studentPo.getStudentId(), reserveRecordPo.getStudentId())) {
            return reserveRecordDao.updateStuFeedback(recordId, feedback)
                    && reserveRecordDao.updateStatus(recordId, ReserveConstant.ENDED);
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean cancelReserveRecord(Integer recordId) {
        ReserveRecordPo reserveRecordPo = reserveRecordDao.queryReserveRecordPoByRecordId(recordId);
        if (ObjectUtils.isEmpty(reserveRecordPo)) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该访谈记录不存在");
        }
        TeacherPo teacherPo = TeacherPoHolder.getTeacherPo();
        StudentPo studentPo = StudentPoHolder.getStudentPo();
        if (!ObjectUtils.isEmpty(studentPo) &&
                !Objects.equals(reserveRecordPo.getStudentId(), studentPo.getStudentId())) {
            throw new BizException(ResultCode.FAILED, "不能取消别人的访谈预约");
        } else if (!ObjectUtils.isEmpty(teacherPo) &&
                !Objects.equals(reserveRecordPo.getJobId(), teacherPo.getJobId())) {
            throw new BizException(ResultCode.FAILED, "不能取消别人的访谈预约");
        }
        if (Objects.equals(reserveRecordPo.getStatus(), ReserveConstant.TO_BE_REVIEWED)
                || Objects.equals(reserveRecordPo.getStatus(), ReserveConstant.PASSED)) {
            return reserveRecordDao.updateStatus(recordId, ReserveConstant.CANCELED)
                    && periodDao.updateIsReservedAndStudentId(reserveRecordPo.getPeriodId(), 0, null);
        } else {
            throw new BizException(ResultCode.FAILED, "错误的状态");
        }
    }

    @Override
    public CommonPage<ReserveRecordDto> queryReserveRecordDtoList(Integer pageNum, Integer pageSize) {
        TeacherPo teacherPo = TeacherPoHolder.getTeacherPo();
        StudentPo studentPo = StudentPoHolder.getStudentPo();
        // 分页参数设置，pageNum 默认为1，pageSize 默认为10
        if (ObjectUtils.isEmpty(pageNum) || pageNum < 0 || pageNum >= 65535) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize) || pageSize < 0 || pageSize >= 65535) {
            pageSize = 10;
        }
        // 开启 pageHelper 自动分页插件
        PageHelper.startPage(pageNum, pageSize);
        if (!ObjectUtils.isEmpty(studentPo)) {
            List<ReserveRecordPo> reserveRecordPoList = reserveRecordDao.queryReserveRecordPoListByStudentId(studentPo.getStudentId());
            CommonPage<ReserveRecordPo> oldCommonPage = CommonPage.restPage(reserveRecordPoList);
            List<ReserveRecordDto> reserveRecordDtoList = BeanConvertor.reserveRecordPoListToDtoList(reserveRecordPoList);
            return CommonPage.setNewList(oldCommonPage, reserveRecordDtoList);
        } else if (!ObjectUtils.isEmpty(teacherDao)) {
            List<ReserveRecordPo> reserveRecordPoList = reserveRecordDao.queryReserveRecordPoListByJobId(teacherPo.getJobId());
            CommonPage<ReserveRecordPo> oldCommonPage = CommonPage.restPage(reserveRecordPoList);
            List<ReserveRecordDto> reserveRecordDtoList = BeanConvertor.reserveRecordPoListToDtoList(reserveRecordPoList);
            return CommonPage.setNewList(oldCommonPage, reserveRecordDtoList);
        } else {
            return null;
        }
    }

    @Override
    public CommonPage<ReserveRecordDto> queryAllReserveRecordDto(RecordSearchVo recordSearchVo) {
        // 分页参数设置，pageNum 默认为1，pageSize 默认为10
        if (ObjectUtils.isEmpty(recordSearchVo.getPageNum()) || recordSearchVo.getPageNum() < 0 ||
                recordSearchVo.getPageNum() >= 65535) {
            recordSearchVo.setPageNum(1);
        }
        if (ObjectUtils.isEmpty(recordSearchVo.getPageSize()) || recordSearchVo.getPageSize() < 0 ||
                recordSearchVo.getPageSize() >= 65535) {
            recordSearchVo.setPageSize(10);
        }
        // 开启 pageHelper 自动分页插件
        PageHelper.startPage(recordSearchVo.getPageNum(), recordSearchVo.getPageSize());
        List<ReserveRecordPo> reserveRecordPoList = reserveRecordDao.queryAllReserveRecordPo(recordSearchVo);
        CommonPage<ReserveRecordPo> oldCommonPage = CommonPage.restPage(reserveRecordPoList);
        List<ReserveRecordDto> reserveRecordDtoList = BeanConvertor.reserveRecordPoListToDtoList(reserveRecordPoList);
        return CommonPage.setNewList(oldCommonPage, reserveRecordDtoList);
    }

    private Boolean isReserveTypeJsonStrValid(String reserveTypeJsonStr) {
        List<Integer> typeIdList = ReserveTypePo.parseReserveType(reserveTypeJsonStr);
        for (Integer typeId : typeIdList) {
            if (ObjectUtils.isEmpty(reserveTypeDao.queryReserveTypeByTypeId(typeId))) {
                return false;
            }
        }
        return true;
    }
}
