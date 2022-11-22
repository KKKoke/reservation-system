package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.dao.CommonPeriodDao;
import com.zksy.reservationsystem.domain.dto.CommonPeriodDto;
import com.zksy.reservationsystem.domain.po.CommonPeriodPo;
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

    private final CommonPeriodDao commonPeriodDao;

    @Override
    public Boolean insertCommonPeriod(String startTime, String endTime, String jobId) {
        Long startTimestamp = TimeConvertor.getValue(startTime);
        Long endTimestamp = TimeConvertor.getValue(endTime);
        return Objects.equals(TeacherPoHolder.getTeacherPo().getJobId(), jobId)  // 不能添加别的老师的常用空闲时间
                && commonPeriodDao.insertCommonPeriod(startTimestamp, endTimestamp, jobId);
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
}
