package com.zksy.reservationsystem.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 访谈记录
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRecordPo {

    /** 编号 */
    private Integer id;

    /** 学号 */
    @NotBlank(message = "studentId can not be null")
    private String studentId;

    /** 工号 */
    @NotBlank(message = "jobId can not be null")
    private String jobId;

    /** 访谈类型，json串 */
    @NotBlank(message = "reserveType can not be null")
    private String reserveType;

    /** 访谈备注 */
    private String comment;

    /** 创建时间 */
    private String createTime;

    /** 开始时间 */
    @NotBlank(message = "startTime can not be null")
    private String startTime;

    /** 结束时间 */
    @NotBlank(message = "endTime can not be null")
    private String endTime;

    /** 学生反馈 */
    private String stuFeedback;

    /** 老师反馈 */
    private String teaFeedback;

    /** 审核状态  1：待审核，2：已通过，3：未通过，4：已结束，5：已取消 */
    private Integer status;

    /** 拒绝原因 */
    private String rejectReason;

    /** 是否删除 */
    private Integer isDeleted;

    /** 空闲时间段id */
    private Integer periodId;
}
