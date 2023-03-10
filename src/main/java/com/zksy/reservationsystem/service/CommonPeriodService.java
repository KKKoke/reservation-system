package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.domain.dto.CommonPeriodDto;

import java.util.List;

/**
 * 常用时间段服务层
 *
 * @author kkkoke
 * @since 2022/11/21
 */
public interface CommonPeriodService {

    /**
     * 新增常用空闲时间段
     */
    Boolean insertCommonPeriod(String startTime, String endTime);

    /**
     * 删除常用空闲时间段
     */
    Boolean deleteCommonPeriod(Integer comPeriodId);

    /**
     * 通过老师id获取常用时间段列表
     */
    List<CommonPeriodDto> queryCommonPeriodDtoListByJobId(String jobId);

    /**
     * 通过常用时间段id获取常用时间段
     */
    CommonPeriodDto queryCommonPeriodDtoByComPeriodId(Integer comPeriodId);

    /**
     * 一键导入常用空闲时间
     */
    Boolean importCommonPeriod(String oneTime);
}
