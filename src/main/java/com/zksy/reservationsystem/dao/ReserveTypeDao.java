package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.po.ReserveTypePo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 访谈类型数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface ReserveTypeDao {

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

    /**
     * 通过访谈选项id获取访谈类型选项包括已删除
     */
    ReserveTypePo queryReserveTypeWithDeletedByTypeId(Integer typeId);
}
