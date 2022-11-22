package com.zksy.reservationsystem.util.common;

import cn.hutool.core.bean.BeanUtil;
import com.zksy.reservationsystem.dao.ReserveTypeDao;
import com.zksy.reservationsystem.dao.StudentDao;
import com.zksy.reservationsystem.dao.TeacherDao;
import com.zksy.reservationsystem.domain.dto.CommonPeriodDto;
import com.zksy.reservationsystem.domain.dto.ReserveRecordDto;
import com.zksy.reservationsystem.domain.po.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeanConvertor {

    private static ReserveTypeDao reserveTypeDao;

    private static TeacherDao teacherDao;

    private static StudentDao studentDao;

    public BeanConvertor(ReserveTypeDao reserveTypeDao, TeacherDao teacherDao, StudentDao studentDao) {
        BeanConvertor.reserveTypeDao = reserveTypeDao;
        BeanConvertor.teacherDao = teacherDao;
        BeanConvertor.studentDao = studentDao;
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
        TeacherPo teacherPo = teacherDao.queryTeacherPoByJobId(reserveRecordPo.getJobId());
        StudentPo studentPo = studentDao.queryStudentPoByStudentId(reserveRecordPo.getStudentId());
        reserveRecordDto.setReserveTypeList(reserveTypeList);
        reserveRecordDto.setTeaName(teacherPo.getName());
        reserveRecordDto.setTeaContact(teacherPo.getContact());
        reserveRecordDto.setStuName(studentPo.getName());
        reserveRecordDto.setStuContact(studentPo.getContact());
        return reserveRecordDto;
    }
}
