package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.service.PeriodService;
import com.zksy.reservationsystem.util.annotation.AuthTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/period")
public class PeriodController {

    private final PeriodService periodService;

    /**
     * 添加空闲时间段
     */
    @AuthTeacher
    @PostMapping("/insertPeriod")
    public CommonResult<?> insertPeriod(@NotBlank(message = "startTime can not be null") String startTime, @NotBlank(message = "endTime can not be null") String endTime) {
        if (periodService.insertPeriod(startTime, endTime)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 通过老师工号获取老师时间段列表
     */
    @GetMapping("/queryPeriodListByJobId")
    public CommonResult<?> queryPeriodDtoListByJobId(@NotBlank(message = "jobId can not be null") String jobId) {
        return CommonResult.success(periodService.queryPeriodDtoListByJobId(jobId));
    }

    /**
     * 根据时间段id删除对应的空闲时间段
     */
    @AuthTeacher
    @PostMapping("/deletePeriod")
    public CommonResult<?> deletePeriod(@NotNull(message = "periodId can not be null") Integer periodId) {
        if (periodService.deletePeriod(periodId)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
