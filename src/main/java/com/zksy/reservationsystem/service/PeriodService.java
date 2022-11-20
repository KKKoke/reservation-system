package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.domain.dto.PeriodDto;

import java.util.List;

/**
 * 空闲时间段服务层
 *
 * @author kkkoke
 * @since 2022/11/20
 */
public interface PeriodService {

    /**
     * 添加空闲时间段
     */
    Boolean insertPeriod(String startTime, String endTime, Integer teacherId);

    /**
     * 通过老师id获取老师时间段列表
     */
    List<PeriodDto> queryPeriodDtoListByTeacherId(Integer teacherId);

    /**
     * 根据时间段id删除对应的空闲时间段
     */
    Boolean deletePeriod(Integer periodId);
}
