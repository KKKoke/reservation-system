package com.zksy.reservationsystem.service;

import com.zksy.reservationsystem.domain.dto.ExportDto;
import com.zksy.reservationsystem.domain.dto.ExportHeadDto;

import java.util.List;

/**
 * 访谈数据导出服务层
 *
 * @author kkkoke
 * @since 2022/11/22
 */
public interface ExportService {

    /**
     * 获取某个老师访谈数据表报表头
     */
    ExportHeadDto queryExportHeadDto(String jobId);

    /**
     * 获取某个老师访谈数据报表
     */
    List<ExportDto> queryExportDto(String jobId);

    /**
     * 获取所有老师访谈数据报表
     */
    List<ExportDto> queryAllExportDto();
}
