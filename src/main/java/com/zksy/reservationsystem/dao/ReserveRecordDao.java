package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.po.ReserveRecordPo;
import com.zksy.reservationsystem.domain.vo.ReserveRecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 访谈记录数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface ReserveRecordDao {

    /**
     * 新增访谈记录
     */
    Boolean insertReserveRecord(ReserveRecordVo reserveRecordVo, String startTime, String endTime, Integer periodId);

    /**
     * 删除访谈记录
     */
    Boolean deleteReserveRecord(Integer recordId);

    /**
     * 根据老师工号获取访谈记录列表
     */
    List<ReserveRecordPo> queryReserveRecordPoListByJobId(String jobId);

    /**
     * 根据学生学号获取访谈记录列表
     */
    List<ReserveRecordPo> queryReserveRecordPoListByStudentId(String studentId);

    /**
     * 根据访谈记录id获取访谈记录
     */
    ReserveRecordPo queryReserveRecordPoByRecordId(Integer recordId);

    /**
     * 更新审核状态
     */
    Boolean updateStatus(Integer recordId, Integer status);

    /**
     * 更新拒绝原因
     */
    Boolean updateRejectReason(Integer recordId, String reason);

    /**
     * 更新学生反馈
     */
    Boolean updateStuFeedback(Integer recordId, String stuFeedback);

    /**
     * 更新老师反馈
     */
    Boolean updateTeaFeedback(Integer recordId, String teaFeedback);
}
