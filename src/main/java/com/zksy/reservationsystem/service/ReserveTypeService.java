package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.domain.po.ReserveTypePo;

import java.util.List;

/**
 * 访谈类型服务层
 *
 * @author kkkoke
 * @since 2022/11/21
 */
public interface ReserveTypeService {

    /**
     * 添加访谈类型选项
     */
    Boolean insertReserveType(String type);

    /**
     * 删除访谈类型选项
     */
    Boolean deleteReserveType(Integer typeId);

    /**
     * 获取访谈类型列表
     */
    List<ReserveTypePo> queryReserveTypeList();

    /**
     * 通过访谈选项id获取访谈类型选项
     */
    ReserveTypePo queryReserveTypeByTypeId(Integer typeId);
}
