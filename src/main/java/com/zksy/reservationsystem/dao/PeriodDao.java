package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.dto.PeriodDto;
import com.zksy.reservationsystem.domain.po.PeriodPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 空闲时间段数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface PeriodDao {

    /**
     * 添加空闲时间段
     */
    Boolean insertPeriod(String startTime, String endTime, String jobId);

    /**
     * 通过老师id获取老师时间段列表
     */
    List<PeriodDto> queryPeriodDtoListByJobId(String jobId);

    /**
     * 根据时间段id删除对应的空闲时间段
     */
    Boolean deletePeriod(Integer periodId);

    /**
     * 通过空闲时间段id查找空时间段
     */
    PeriodPo queryPeriodPoByPeriodId(Integer periodId);

    /**
     * 更新学号和是否被预约
     */
    Boolean updateIsReservedAndStudentId(Integer periodId, Integer isReserved, String studentId);
}
