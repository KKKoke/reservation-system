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
    public Boolean insertPeriod(String startTime, String endTime, Integer teacherId) {
        // 不能添加别的老师的空闲时间
        return Objects.equals(TeacherPoHolder.getTeacherPo().getId(), teacherId) && periodDao.insertPeriod(startTime, endTime, teacherId);
    }

    @Override
    public List<PeriodDto> queryPeriodDtoListByTeacherId(Integer teacherId) {
        return periodDao.queryPeriodDtoListByTeacherId(teacherId);
    }

    @Override
    public Boolean deletePeriod(Integer periodId) {
        PeriodPo periodPo = periodDao.queryPeriodPoByPeriodId(periodId);
        return !ObjectUtils.isEmpty(periodDao.queryPeriodPoByPeriodId(periodId))
                && Objects.equals(TeacherPoHolder.getTeacherPo().getId(), periodPo.getTeacherId()) && periodDao.deletePeriod(periodId);
    }
}
