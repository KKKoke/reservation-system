package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.dao.ReserveTypeDao;
import com.zksy.reservationsystem.domain.po.ReserveTypePo;
import com.zksy.reservationsystem.service.ReserveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 访谈类型服务层实现类
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Service
@RequiredArgsConstructor
public class ReserveTypeServiceImpl implements ReserveTypeService {

    private final ReserveTypeDao reserveTypeDao;

    @Override
    public Boolean insertReserveType(String type) {
        return reserveTypeDao.insertReserveType(type);
    }

    @Override
    public Boolean deleteReserveType(Integer typeId) {
        return !ObjectUtils.isEmpty(reserveTypeDao.queryReserveTypeByTypeId(typeId))
                && reserveTypeDao.deleteReserveType(typeId);
    }

    @Override
    public List<ReserveTypePo> queryReserveTypeList() {
        return reserveTypeDao.queryReserveTypeList();
    }

    @Override
    public ReserveTypePo queryReserveTypeByTypeId(Integer typeId) {
        return reserveTypeDao.queryReserveTypeByTypeId(typeId);
    }
}
