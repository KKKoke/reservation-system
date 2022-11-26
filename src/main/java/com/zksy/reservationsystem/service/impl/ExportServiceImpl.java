package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.ExportDao;
import com.zksy.reservationsystem.dao.ReserveRecordDao;
import com.zksy.reservationsystem.dao.ReserveTypeDao;
import com.zksy.reservationsystem.dao.TeacherDao;
import com.zksy.reservationsystem.domain.dto.ExportDto;
import com.zksy.reservationsystem.domain.dto.ExportHeadDto;
import com.zksy.reservationsystem.domain.po.ReserveRecordPo;
import com.zksy.reservationsystem.domain.po.ReserveTypePo;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 访谈数据导出服务层实现类
 *
 * @author kkkoke
 * @since 2022/11/22
 */
@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final ReserveRecordDao reserveRecordDao;

    private final TeacherDao teacherDao;

    private final ExportDao exportDao;

    private final ReserveTypeDao reserveTypeDao;

    @Override
    public ExportHeadDto queryExportHeadDto(String jobId) {
        TeacherPo teacherPo = teacherDao.queryTeacherPoByJobId(jobId);
        if (ObjectUtils.isEmpty(teacherPo)) {
            throw new BizException(ResultCode.FAILED, "该老师不存在");
        }
        List<ReserveRecordPo> reserveRecordPoList = reserveRecordDao.queryReserveRecordPoListByJobId(jobId);
        return new ExportHeadDto(jobId, teacherPo.getName(), teacherPo.getContact(), reserveRecordPoList.size());
    }

    @Override
    public List<ExportDto> queryExportDto(String jobId) {
        List<ExportDto> exportDtoList = exportDao.queryExportDto(jobId);
        if (ObjectUtils.isEmpty(exportDtoList)) {
            throw new BizException(ResultCode.FAILED, "当前老师没有访谈数据");
        }
        exportDtoList.forEach(exportDto -> {
            StringBuilder reserveType = new StringBuilder();
            for (Integer typeId : ReserveTypePo.parseReserveType(exportDto.getReserveType())) {
                reserveType.append(reserveTypeDao.queryReserveTypeByTypeId(typeId).getType());
                reserveType.append(";");
            }
            exportDto.setReserveType(String.valueOf(reserveType));
        });
        return exportDtoList;
    }

    @Override
    public List<ExportDto> queryAllExportDto() {
        List<ExportDto> exportDtoList = exportDao.queryAllExportDto();
        if (ObjectUtils.isEmpty(exportDtoList)) {
            throw new BizException(ResultCode.FAILED, "当前没有访谈数据");
        }
        exportDtoList.forEach(exportDto -> {
            StringBuilder reserveType = new StringBuilder();
            for (Integer typeId : ReserveTypePo.parseReserveType(exportDto.getReserveType())) {
                reserveType.append(reserveTypeDao.queryReserveTypeByTypeId(typeId).getType());
                reserveType.append(";");
            }
            exportDto.setReserveType(String.valueOf(reserveType));
        });
        return exportDtoList;
    }
}
