package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.dto.ExportDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 访谈数据导出数据库操作类
 *
 * @author kkkoke
 * @since 2022/11/22
 */
@Mapper
public interface ExportDao {

    /**
     * 获取某个老师访谈数据报表
     */
    List<ExportDto> queryExportDto(String jobId);
}
