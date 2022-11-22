package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.service.CommonPeriodService;
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
@RequestMapping("/v1/common")
public class CommonPeriodController {

    private final CommonPeriodService commonPeriodService;

    /**
     * 添加常用空闲时间段
     */
    @AuthTeacher
    @PostMapping("/insertCommonPeriod")
    public CommonResult<?> insertCommonPeriod(@NotBlank(message = "startTime can not be null") String startTime, @NotBlank(message = "endTime can not be null") String endTime,
                                              @NotBlank(message = "jobId can not be null") String jobId) {
        if (commonPeriodService.insertCommonPeriod(startTime, endTime, jobId)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 通过老师工号获取老师常用空闲时间段列表
     */
    @GetMapping("/queryCommonPeriodListByJobId")
    public CommonResult<?> queryCommonPeriodDtoListByJobId(@NotBlank(message = "jobId can not be null") String jobId) {
        return CommonResult.success(commonPeriodService.queryCommonPeriodDtoListByJobId(jobId));
    }

    /**
     * 通过常用空闲时间段id获取老师常用空闲时间段列表
     */
    @GetMapping("/queryCommonPeriodByComPeriodId")
    public CommonResult<?> queryCommonPeriodDtoByComPeriodId(@NotNull(message = "comPeriodId can not be null") Integer comPeriodId) {
        return CommonResult.success(commonPeriodService.queryCommonPeriodDtoByComPeriodId(comPeriodId));
    }

    /**
     * 删除常用空闲时间段
     */
    @AuthTeacher
    @PostMapping("/deleteCommonPeriod")
    public CommonResult<?> deleteCommonPeriod(@NotNull(message = "comPeriodId can not be null") Integer comPeriodId) {
        if (commonPeriodService.deleteCommonPeriod(comPeriodId)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
