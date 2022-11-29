package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.domain.vo.StuUpdateVo;
import com.zksy.reservationsystem.domain.vo.StudentVo;
import com.zksy.reservationsystem.service.StudentService;
import com.zksy.reservationsystem.util.annotation.AuthTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @AuthTeacher
    @PostMapping("/insertStudent")
    public CommonResult<?> insertStudent(@Valid @RequestBody StudentVo studentVo) {
        if (studentService.insertStudent(studentVo)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 删除学生
     */
    @AuthTeacher
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
    public CommonResult<?> queryStudentDtoList(String name, String studentId, String className, String dormitory, Integer pageNum, Integer pageSize) {
        return CommonResult.success(studentService.queryStudentDtoList(name, studentId, className, dormitory, pageNum, pageSize));
    }

    /**
     * 通过学号获取学生信息
     */
    @GetMapping("/queryStudentByStudentId")
    public CommonResult<?> queryStudentDtoByStudentId(@NotBlank(message = "studentId can not be null") String studentId) {
        return CommonResult.success(studentService.queryStudentDtoByStudentId(studentId));
    }

    /**
     * 修改学生信息
     */
    @AuthTeacher
    @PostMapping("/updateStudent")
    public CommonResult<?> updateStudent(@RequestBody StuUpdateVo stuUpdateVo) {
        if (studentService.updateStudent(stuUpdateVo)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
