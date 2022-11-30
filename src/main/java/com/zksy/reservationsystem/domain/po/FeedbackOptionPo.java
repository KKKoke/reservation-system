package com.zksy.reservationsystem.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生反馈内容选项
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackOptionPo {

    /** 编号 */
    private Integer id;

    /** 反馈选项内容 */
    private String option;

    /** 是否删除 */
    private Integer isDeleted;
}
