package com.zksy.reservationsystem.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 空闲时间段
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodPo {

    /** 编号 */
    private Integer id;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 所属老师id */
    private Integer teacherId;
}
