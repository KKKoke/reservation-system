package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.common.CommonPage;
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
    CommonPage<ReserveRecordDto> queryReserveRecordDtoListByJobId(String jobId, Integer pageNum, Integer pageSize);

    /**
     * 根据学生学号获取访谈记录列表
     */
    CommonPage<ReserveRecordDto> queryReserveRecordDtoListByStudentId(String studentId, Integer pageNum, Integer pageSize);

    /**
     * 老师审核访谈预约
     */
    Boolean checkReserveRecord(Integer recordId, Integer status, String rejectReason);

    /**
     * 访谈结束后填写反馈
     */
    Boolean submitFeedback(Integer recordId, String feedback);

    /**
     * 取消访谈预约
     */
    Boolean cancelReserveRecord(Integer recordId);

    /**
     * 获取访谈列表
     */
    CommonPage<ReserveRecordDto> queryReserveRecordDtoList(Integer pageNum, Integer pageSize);
}
