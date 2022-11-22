package com.zksy.reservationsystem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 访谈记录值对象
 *
 * @author kkkoke
 * @since 2022/11/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRecordVo {

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

    @NotNull(message = "periodId can not be null")
    private Integer periodId;
}
