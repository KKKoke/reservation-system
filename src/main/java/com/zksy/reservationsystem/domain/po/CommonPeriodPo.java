package com.zksy.reservationsystem.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 常用空闲时间段
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPeriodPo {

    /** 编号 */
    private Integer id;

    /** 从0点到开始时间的毫秒数 */
    private Long startTimestamp;

    /** 从0点到结束时间的毫秒数 */
    private Long endTimestamp;

    /** 所属老师id */
    private Integer teacherId;
}
