package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.service.TeacherService;
import com.zksy.reservationsystem.util.annotation.AuthAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 老师控制层
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    /**
     * 新增老师
     */
    @AuthAdmin
    @PostMapping("/insertTeacher")
    public CommonResult<?> insertTeacher(@NotBlank(message = "name can not be null") String name, @NotBlank(message = "jobId can not be null") String jobId,
                                         @NotBlank(message = "password can not be null") String password, @NotBlank(message = "contact can not be null") String contact) {
        if (teacherService.insertTeacher(name, jobId, password, contact)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 删除老师
     */
    @AuthAdmin
    @PostMapping("/deleteTeacher")
    public CommonResult<?> deleteTeacher(@NotBlank(message = "jobId can not be null") String jobId) {
        if (teacherService.deleteTeacher(jobId)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 获取老师列表
     */
    @GetMapping("/queryTeacherList")
    public CommonResult<?> queryTeacherDtoList() {
        return CommonResult.success(teacherService.queryTeacherDtoList());
    }

    /**
     * 通过工号获取老师信息
     */
    @GetMapping("/queryTeacherByJobId")
    public CommonResult<?> queryTeacherDtoByJobId(@NotBlank(message = "jobId can not be null") String jobId) {
        return CommonResult.success(teacherService.queryTeacherDtoByJobId(jobId));
    }
}
