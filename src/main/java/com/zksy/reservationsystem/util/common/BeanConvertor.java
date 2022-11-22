package com.zksy.reservationsystem.util.common;

import cn.hutool.core.bean.BeanUtil;
import com.zksy.reservationsystem.dao.ReserveTypeDao;
import com.zksy.reservationsystem.domain.dto.CommonPeriodDto;
import com.zksy.reservationsystem.domain.dto.ReserveRecordDto;
import com.zksy.reservationsystem.domain.po.CommonPeriodPo;
import com.zksy.reservationsystem.domain.po.ReserveRecordPo;
import com.zksy.reservationsystem.domain.po.ReserveTypePo;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeanConvertor {

    private static ReserveTypeDao reserveTypeDao;

    public BeanConvertor(ReserveTypeDao reserveTypeDao) {
        BeanConvertor.reserveTypeDao = reserveTypeDao;
    }

    public static CommonPeriodDto commonPeriodPoToCommonPeriodDto(CommonPeriodPo commonPeriodPo) {
        if (ObjectUtils.isEmpty(commonPeriodPo)) {
            return null;
        }
        String startTime = TimeConvertor.getHM(commonPeriodPo.getStartTimestamp());
        String endTime = TimeConvertor.getHM(commonPeriodPo.getEndTimestamp());
        return new CommonPeriodDto(commonPeriodPo.getId(), startTime, endTime, commonPeriodPo.getJobId());
    }

    public static List<ReserveRecordDto> reserveRecordPoListToDtoList(List<ReserveRecordPo> reserveRecordPoList) {
        if (ObjectUtils.isEmpty(reserveRecordPoList)) {
            return null;
        }
        List<ReserveRecordDto> reserveRecordDtoList = new ArrayList<>();
        reserveRecordPoList.forEach(reserveRecordPo -> {
            reserveRecordDtoList.add(reserveRecordPoToDto(reserveRecordPo));
        });
        return reserveRecordDtoList;
    }

    public static ReserveRecordDto reserveRecordPoToDto(ReserveRecordPo reserveRecordPo) {
        if (ObjectUtils.isEmpty(reserveRecordPo)) {
            return null;
        }
        ReserveRecordDto reserveRecordDto = BeanUtil.copyProperties(reserveRecordPo, ReserveRecordDto.class);
        List<String> reserveTypeList = new ArrayList<>();
        for (Integer typeId : ReserveTypePo.parseReserveType(reserveRecordPo.getReserveType())) {
            reserveTypeList.add(reserveTypeDao.queryReserveTypeByTypeId(typeId).getType());
        }
        reserveRecordDto.setReserveTypeList(reserveTypeList);
        return reserveRecordDto;
    }
}
