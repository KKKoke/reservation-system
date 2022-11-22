package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.*;
import com.zksy.reservationsystem.domain.dto.ReserveRecordDto;
import com.zksy.reservationsystem.domain.po.PeriodPo;
import com.zksy.reservationsystem.domain.po.ReserveRecordPo;
import com.zksy.reservationsystem.domain.po.ReserveTypePo;
import com.zksy.reservationsystem.domain.vo.ReserveRecordVo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.ReserveRecordService;
import com.zksy.reservationsystem.util.common.BeanConvertor;
import com.zksy.reservationsystem.util.constant.ReserveConstant;
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
    public List<ReserveRecordDto> queryReserveRecordDtoListByJobId(String jobId) {
        if (ObjectUtils.isEmpty(teacherDao.queryTeacherPoByJobId(jobId))) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该老师不存在");
        }
        return BeanConvertor.reserveRecordPoListToDtoList(reserveRecordDao.queryReserveRecordPoListByJobId(jobId));
    }

    @Override
    public List<ReserveRecordDto> queryReserveRecordDtoListByStudentId(String studentId) {
        if (ObjectUtils.isEmpty(studentDao.queryStudentPoByStudentId(studentId))) {
            throw new BizException(ResultCode.VALIDATE_FAILED, "该学生不存在");
        }
        return BeanConvertor.reserveRecordPoListToDtoList(reserveRecordDao.queryReserveRecordPoListByStudentId(studentId));
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
            reserveRecordDao.updateStatus(recordId, status);
        }
        // 审核不通过，需要说明拒绝原因
        else if (Objects.equals(status, ReserveConstant.NOT_PASSED)) {
            if (ObjectUtils.isEmpty(rejectReason)) {
                throw new BizException(ResultCode.VALIDATE_FAILED, "请说明拒绝理由");
            }
            reserveRecordDao.updateStatus(recordId, status);
            reserveRecordDao.updateRejectReason(recordId, rejectReason);
            periodDao.updateIsReservedAndStudentId(reserveRecordPo.getPeriodId(), 0, null);
        } else {
            throw new BizException(ResultCode.VALIDATE_FAILED, "不合法的状态值");
        }
        return true;
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
