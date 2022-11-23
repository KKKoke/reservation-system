package com.zksy.reservationsystem.domain.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结果导出类
 *
 * @author kkkoke
 * @since 2022/11/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportDto {

    @Excel(name = "学号", width = 20)
    private String studentId;

    @Excel(name = "姓名", width = 20)
    private String name;

    @Excel(name = "班级", width = 20)
    private String className;

    @Excel(name = "性别", width = 20)
    private String gender;

    @Excel(name = "联系方式", width = 25)
    private String contact;

    @Excel(name = "家长联系方式", width = 25)
    private String parentContact;

    @Excel(name = "寝室号", width = 20)
    private String dormitory;

    @Excel(name = "访谈类型", width = 20)
    private String reserveType;

    @Excel(name = "情况描述", width = 40)
    private String comment;

    @Excel(name = "开始时间", width = 20)
    private String startTime;

    @Excel(name = "结束时间", width = 20)
    private String endTime;

    @Excel(name = "访谈记录", width = 60)
    private String teaFeedback;

    @Excel(name = "学生反馈", width = 60)
    private String stuFeedback;
}
