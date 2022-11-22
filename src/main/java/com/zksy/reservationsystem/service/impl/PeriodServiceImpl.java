package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.dao.PeriodDao;
import com.zksy.reservationsystem.domain.dto.PeriodDto;
import com.zksy.reservationsystem.domain.po.PeriodPo;
import com.zksy.reservationsystem.service.PeriodService;
import com.zksy.reservationsystem.util.holder.TeacherPoHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 空闲时间段服务层实现类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final PeriodDao periodDao;

    @Override
    public Boolean insertPeriod(String startTime, String endTime, String jobId) {
        return Objects.equals(TeacherPoHolder.getTeacherPo().getJobId(), jobId)  // 不能添加别的老师的空闲时间
                && periodDao.insertPeriod(startTime, endTime, jobId);
    }

    @Override
    public List<PeriodDto> queryPeriodDtoListByJobId(String jobId) {
        return periodDao.queryPeriodDtoListByJobId(jobId);
    }

    @Override
    public Boolean deletePeriod(Integer periodId) {
        PeriodPo periodPo = periodDao.queryPeriodPoByPeriodId(periodId);
        return !ObjectUtils.isEmpty(periodPo)
                && Objects.equals(TeacherPoHolder.getTeacherPo().getId(), periodPo.getJobId()) && periodDao.deletePeriod(periodId);
    }
}
