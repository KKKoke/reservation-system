package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.po.CommonPeriodPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 常用空闲时间段数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface CommonPeriodDao {

    /**
     * 新增常用空闲时间段
     */
    Boolean insertCommonPeriod(Long startTimestamp, Long endTimestamp, Integer teacherId);

    /**
     * 删除常用空闲时间段
     */
    Boolean deleteCommonPeriod(Integer comPeriodId);

    /**
     * 通过老师id获取常用时间段列表
     */
    List<CommonPeriodPo> queryCommonPeriodPoListByTeacherId(Integer teacherId);

    /**
     * 通过常用时间段id获取常用时间段
     */
    CommonPeriodPo queryCommonPeriodPoByComPeriodId(Integer comPeriodId);
}
