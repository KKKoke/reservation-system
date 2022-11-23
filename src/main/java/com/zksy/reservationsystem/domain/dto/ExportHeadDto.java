package com.zksy.reservationsystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报表表头信息
 *
 * @author kkkoke
 * @since 2022/11/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportHeadDto {

    /** 老师工号 */
    private String jobId;

    /** 老师姓名 */
    private String teaName;

    /** 老师联系方式 */
    private String teaContact;

    /** 访谈数量 */
    private Integer reserveNum;
}
