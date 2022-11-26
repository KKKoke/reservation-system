package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.domain.vo.ReserveRecordVo;
import com.zksy.reservationsystem.service.ReserveRecordService;
import com.zksy.reservationsystem.util.annotation.AuthTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 访谈记录控制层
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/record")
public class ReserveRecordController {

    private final ReserveRecordService reserveRecordService;

    /**
     * 新增访谈记录
     */
    @PostMapping("/insertReserveRecord")
    public CommonResult<?> insertReserveRecord(@Valid @RequestBody ReserveRecordVo reserveRecordVo) {
        if (reserveRecordService.insertReserveRecord(reserveRecordVo)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 根据老师工号获取访谈记录列表
     */
    @GetMapping("/queryRecordListByJobId")
    public CommonResult<?> queryReserveRecordDtoListByJobId(@NotBlank(message = "jobId can not be null") String jobId) {
        return CommonResult.success(reserveRecordService.queryReserveRecordDtoListByJobId(jobId));
    }

    /**
     * 根据学生学号获取访谈记录列表
     */
    @GetMapping("/queryRecordListByStudentId")
    public CommonResult<?> queryReserveRecordDtoListByStudentId(@NotBlank(message = "studentId can not be null") String studentId) {
        return CommonResult.success(reserveRecordService.queryReserveRecordDtoListByStudentId(studentId));
    }

    /**
     * 获取访谈列表
     */
    @GetMapping("/queryReserveRecordList")
    public CommonResult<?> queryReserveRecordDtoList() {
        return CommonResult.success(reserveRecordService.queryReserveRecordDtoList());
    }

    /**
     * 老师审核访谈预约
     */
    @AuthTeacher
    @PostMapping("/checkReserveRecord")
    public CommonResult<?> checkReserveRecord(@NotNull(message = "recordId can not be null") Integer recordId,
                                              @NotNull(message = "status can not be null") Integer status, String rejectReason) {
        if (reserveRecordService.checkReserveRecord(recordId, status, rejectReason)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 访谈结束后填写反馈
     */
    @PostMapping("/submitFeedback")
    public CommonResult<?> submitFeedback(@NotNull(message = "recordId can not be null") Integer recordId,
                                          @NotBlank(message = "feedback can not be null") String feedback) {
        if (reserveRecordService.submitFeedback(recordId, feedback)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 取消访谈预约
     */
    @PostMapping("/cancelReserveRecord")
    public CommonResult<?> cancelReserveRecord(@NotNull(message = "recordId can not be null") Integer recordId) {
        if (reserveRecordService.cancelReserveRecord(recordId)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
