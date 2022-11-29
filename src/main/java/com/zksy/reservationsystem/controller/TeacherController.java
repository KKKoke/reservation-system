package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.domain.vo.TeaUpdateVo;
import com.zksy.reservationsystem.domain.vo.TeacherVo;
import com.zksy.reservationsystem.service.TeacherService;
import com.zksy.reservationsystem.util.annotation.AuthAdmin;
import com.zksy.reservationsystem.util.annotation.AuthTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public CommonResult<?> insertTeacher(@Valid @RequestBody TeacherVo teacherVo) {
        if (teacherService.insertTeacher(teacherVo)) {
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
    public CommonResult<?> queryTeacherDtoList(String name, String jobId, Integer type, Integer pageNum, Integer pageSize) {
        return CommonResult.success(teacherService.queryTeacherDtoList(name, jobId, type, pageNum, pageSize));
    }

    /**
     * 通过工号获取老师信息
     */
    @GetMapping("/queryTeacherByJobId")
    public CommonResult<?> queryTeacherDtoByJobId(@NotBlank(message = "jobId can not be null") String jobId) {
        return CommonResult.success(teacherService.queryTeacherDtoByJobId(jobId));
    }

    /**
     * 修改老师信息
     */
    @AuthTeacher
    @PostMapping("/updateTeacher")
    public CommonResult<?> updateTeacher(@RequestBody TeaUpdateVo teaUpdateVo) {
        if (teacherService.updateTeacher(teaUpdateVo)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
