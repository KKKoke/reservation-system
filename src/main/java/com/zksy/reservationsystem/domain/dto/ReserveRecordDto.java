package com.zksy.reservationsystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 访谈记录接口返回类
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRecordDto {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 工号
     */
    private String jobId;

    /**
     * 访谈类型
     */
    private List<String> reserveTypeList;

    /**
     * 访谈备注
     */
    private String comment;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 学生反馈
     */
    private String stuFeedback;

    /**
     * 老师反馈
     */
    private String teaFeedback;

    /**
     * 审核状态  1：待审核，2：已通过，3：未通过，4：已结束
     */
    private Integer status;

    /**
     * 拒绝原因
     */
    private String rejectReason;
}
