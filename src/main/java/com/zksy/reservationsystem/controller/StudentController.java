package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.service.StudentService;
import com.zksy.reservationsystem.util.annotation.AuthAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 学生控制层
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student")
public class StudentController {

    private final StudentService studentService;

    /**
     * 新增学生
     */
    @AuthAdmin
    @PostMapping("/insertStudent")
    public CommonResult<?> insertStudent(@NotBlank(message = "name can not be null") String name, @NotBlank(message = "studentId can not be null") String studentId,
                                         @NotBlank(message = "password can not be null") String password, String contact) {
        if (studentService.insertStudent(name, studentId, password, contact)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 删除学生
     */
    @AuthAdmin
    @PostMapping("/deleteStudent")
    public CommonResult<?> deleteStudent(@NotBlank(message = "studentId can not be null") String studentId) {
        if (studentService.deleteStudent(studentId)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 获取学生列表
     */
    @GetMapping("/queryStudentList")
    public CommonResult<?> queryStudentDtoList(String name, String studentId) {
        return CommonResult.success(studentService.queryStudentDtoList(name, studentId));
    }

    /**
     * 通过学号获取学生信息
     */
    @GetMapping("/queryStudentByStudentId")
    public CommonResult<?> queryStudentDtoByStudentId(@NotBlank(message = "studentId can not be null") String studentId) {
        return CommonResult.success(studentService.queryStudentDtoByStudentId(studentId));
    }
}
