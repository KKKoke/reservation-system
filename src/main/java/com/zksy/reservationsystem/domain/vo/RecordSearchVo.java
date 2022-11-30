package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访谈记录搜索值对象
 *
 * @author kkkoke
 * @since 2022/11/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordSearchVo {

    /** 学号 */
    private String studentId;

    /** 学生姓名 */
    private String stuName;

    /** 工号 */
    private String jobId;

    /** 老师姓名 */
    private String teaName;

    /** 访谈类型 */
    private String reserveType;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 审核状态 */
    private Integer status;

    /** 页数 */
    private Integer pageNum;

    /** 每页数量 */
    private Integer pageSize;
}
