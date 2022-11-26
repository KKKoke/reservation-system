package com.zksy.reservationsystem.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.zksy.reservationsystem.domain.dto.ExportDto;
import com.zksy.reservationsystem.domain.dto.ExportHeadDto;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.service.ExportService;
import com.zksy.reservationsystem.util.annotation.AuthAdmin;
import com.zksy.reservationsystem.util.holder.TeacherPoHolder;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 访谈数据导出控制层
 *
 * @author kkkoke
 * @since 2022/11/23
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/export")
public class ExportController {

    private final ExportService exportService;

    /**
     * 导出某个老师访谈数据报表
     */
    @GetMapping("/exportRecordOfOneTeacher")
    public void exportRecordOfOneTeacher(@NotBlank(message = "jobId can not be null") String jobId, HttpServletResponse response) throws IOException {
        TeacherPo teacherPo = TeacherPoHolder.getTeacherPo();
        ExportHeadDto exportHeadDto = exportService.queryExportHeadDto(jobId);
        List<ExportDto> exportDtoList = exportService.queryExportDto(jobId);
        ExportParams exportParams = new ExportParams("访谈记录报表" + "-" + exportHeadDto.getTeaName() +
                "-" + exportHeadDto.getJobId(), LocalDateTime.now().toString());
        exportParams.setSecondTitle("导出人：" + teacherPo.getName() +
                "  导出时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        exportParams.setTitleHeight((short) 8);
        exportParams.setHeaderHeight(8);
        exportParams.setHeight((short) 6);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportDto.class, exportDtoList);
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=%E6%95%B0%E6%8D%AE%E5%AF%BC%E5%87%BA_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }

    /**
     * 导出所有老师访谈数据报表
     */
    @AuthAdmin
    @GetMapping("/exportAllRecord")
    public void exportAllRecord(HttpServletResponse response) throws IOException {
        TeacherPo teacherPo = TeacherPoHolder.getTeacherPo();
        List<ExportDto> exportDtoList = exportService.queryAllExportDto();
        ExportParams exportParams = new ExportParams("访谈记录汇总报表", LocalDateTime.now().toString());
        exportParams.setSecondTitle("导出人：" + teacherPo.getName() +
                "  导出时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        exportParams.setTitleHeight((short) 8);
        exportParams.setHeaderHeight(8);
        exportParams.setHeight((short) 6);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportDto.class, exportDtoList);
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=%E6%95%B0%E6%8D%AE%E5%AF%BC%E5%87%BA_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }
}
