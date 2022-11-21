package com.zksy.reservationsystem.util.common;

import com.zksy.reservationsystem.domain.dto.CommonPeriodDto;
import com.zksy.reservationsystem.domain.po.CommonPeriodPo;
import org.springframework.util.ObjectUtils;

public class BeanUtil {

    public static CommonPeriodDto commonPeriodPoToCommonPeriodDto(CommonPeriodPo commonPeriodPo) {
        if (ObjectUtils.isEmpty(commonPeriodPo)) {
            return null;
        }
        String startTime = TimeConvertor.getHM(commonPeriodPo.getStartTimestamp());
        String endTime = TimeConvertor.getHM(commonPeriodPo.getEndTimestamp());
        return new CommonPeriodDto(commonPeriodPo.getId(), startTime, endTime, commonPeriodPo.getTeacherId());
    }
}
