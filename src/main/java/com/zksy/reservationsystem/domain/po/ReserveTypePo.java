package com.zksy.reservationsystem.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访谈类型
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveTypePo {

    /** 编号 */
    private Integer id;

    /** 类型名称 */
    private String type;
}
