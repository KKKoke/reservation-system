package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.domain.dto.ReserveRecordDto;
import com.zksy.reservationsystem.domain.vo.ReserveRecordVo;

import java.util.List;

/**
 * 访谈记录服务层
 *
 * @author kkkoke
 * @since 2022/11/21
 */
public interface ReserveRecordService {

    /**
     * 新增访谈记录
     */
    Boolean insertReserveRecord(ReserveRecordVo reserveRecordVo);

    /**
     * 根据老师工号获取访谈记录列表
     */
    List<ReserveRecordDto> queryReserveRecordDtoListByJobId(String jobId);

    /**
     * 根据学生学号获取访谈记录列表
     */
    List<ReserveRecordDto> queryReserveRecordDtoListByStudentId(String studentId);
}
