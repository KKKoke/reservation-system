package com.zksy.reservationsystem.domain.po;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    /** 是否删除 */
    private Integer isDeleted;

    public static List<Integer> parseReserveType(String reserveTypeJsonStr) {
        return JSONUtil.toList(JSONUtil.parseArray(reserveTypeJsonStr), Integer.class);
    }
}
