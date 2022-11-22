package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.CommonPeriodDao;
import com.zksy.reservationsystem.dao.PeriodDao;
import com.zksy.reservationsystem.domain.dto.CommonPeriodDto;
import com.zksy.reservationsystem.domain.po.CommonPeriodPo;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.CommonPeriodService;
import com.zksy.reservationsystem.util.common.BeanConvertor;
import com.zksy.reservationsystem.util.common.TimeConvertor;
import com.zksy.reservationsystem.util.holder.TeacherPoHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 常用时间段服务层实现类
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Service
@RequiredArgsConstructor
public class CommonPeriodServiceImpl implements CommonPeriodService {

    private final PeriodDao periodDao;

    private final CommonPeriodDao commonPeriodDao;

    @Override
    public Boolean insertCommonPeriod(String startTime, String endTime) {
        Long startTimestamp = TimeConvertor.getValue(startTime);
        Long endTimestamp = TimeConvertor.getValue(endTime);
        return commonPeriodDao.insertCommonPeriod(startTimestamp, endTimestamp, TeacherPoHolder.getTeacherPo().getJobId());
    }

    @Override
    public Boolean deleteCommonPeriod(Integer comPeriodId) {
        CommonPeriodPo commonPeriodPo = commonPeriodDao.queryCommonPeriodPoByComPeriodId(comPeriodId);
        return !ObjectUtils.isEmpty(commonPeriodPo)
                && Objects.equals(TeacherPoHolder.getTeacherPo().getJobId(), commonPeriodPo.getJobId())
                && commonPeriodDao.deleteCommonPeriod(comPeriodId);
    }

    @Override
    public List<CommonPeriodDto> queryCommonPeriodDtoListByJobId(String jobId) {
        List<CommonPeriodPo> commonPeriodPoList = commonPeriodDao.queryCommonPeriodPoListByJobId(jobId);
        List<CommonPeriodDto> commonPeriodDtoList = new ArrayList<>();
        commonPeriodPoList.forEach(commonPeriodPo -> {
            commonPeriodDtoList.add(BeanConvertor.commonPeriodPoToCommonPeriodDto(commonPeriodPo));
        });
        return commonPeriodDtoList;
    }

    @Override
    public CommonPeriodDto queryCommonPeriodDtoByComPeriodId(Integer comPeriodId) {
        return BeanConvertor.commonPeriodPoToCommonPeriodDto(commonPeriodDao.queryCommonPeriodPoByComPeriodId(comPeriodId));
    }

    @Override
    public Boolean importCommonPeriod(String oneTime) {
        TeacherPo teacherPo = TeacherPoHolder.getTeacherPo();
        List<CommonPeriodPo> commonPeriodPoList = commonPeriodDao.queryCommonPeriodPoListByJobId(teacherPo.getJobId());
        List<CommonPeriodDto> commonPeriodDtoList = TimeConvertor.dayTimeListToSomeDayTimeList(oneTime, commonPeriodPoList);
        if (ObjectUtils.isEmpty(commonPeriodDtoList)) {
            throw new BizException(ResultCode.FAILED, "该老师没有设置常用空闲时间段");
        }
        commonPeriodDtoList.forEach(commonPeriodDto -> {
            periodDao.insertPeriod(commonPeriodDto.getStartTime(), commonPeriodDto.getEndTime(), teacherPo.getJobId());
        });
        return true;
    }
}
