package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.service.FeedbackOptionService;
import com.zksy.reservationsystem.util.annotation.AuthAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 学生反馈选项控制层
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/option")
public class FeedbackOptionController {

    private final FeedbackOptionService feedbackOptionService;

    /**
     * 新增学生反馈选项
     */
    @AuthAdmin
    @PostMapping("/insertFeedbackOption")
    public CommonResult<?> insertFeedbackOption(@NotBlank(message = "option can not be null") String option) {
        if (feedbackOptionService.insertFeedbackOption(option)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 删除学生反馈选项
     */
    @AuthAdmin
    @PostMapping("/deleteFeedbackOption")
    public CommonResult<?> deleteFeedbackOption(@NotNull(message = "optionId can not be null") Integer optionId) {
        if (feedbackOptionService.deleteFeedbackOption(optionId)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 获取学生反馈选项列表
     */
    @GetMapping("/queryFeedbackOptionList")
    public CommonResult<?> queryFeedbackOptionList() {
        return CommonResult.success(feedbackOptionService.queryFeedbackOptionList());
    }

    /**
     * 通过反馈选项id获取反馈选项
     */
    @GetMapping("/queryOptionByOptionId")
    public CommonResult<?> queryFeedbackOptionByOptionId(@NotNull(message = "optionId can not be null") Integer optionId) {
        return CommonResult.success(feedbackOptionService.queryFeedbackOptionByOptionId(optionId));
    }
}
