package com.zksy.reservationsystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 常用空闲时间段接口返回类
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPeriodDto {

    /** 编号 */
    private Integer id;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 老师工号 */
    private String jobId;
}
