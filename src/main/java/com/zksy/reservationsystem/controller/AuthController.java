package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.domain.dto.StudentDto;
import com.zksy.reservationsystem.domain.dto.TeacherDto;
import com.zksy.reservationsystem.domain.vo.AuthVo;
import com.zksy.reservationsystem.service.JwtService;
import com.zksy.reservationsystem.service.StudentService;
import com.zksy.reservationsystem.service.TeacherService;
import com.zksy.reservationsystem.util.constant.JwtConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限认证控制层
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private final JwtService jwtService;

    private final TeacherService teacherService;

    private final StudentService studentService;

    /**
     * 学生登录
     */
    @PostMapping("/stuLogin")
    public CommonResult<?> stuLogin(@RequestBody AuthVo authVo) {
        String token = jwtService.login(JwtConstant.STU_LOGIN_TYPE, authVo.getUsername(), authVo.getPassword());
        if (ObjectUtils.isEmpty(token)) {
            return CommonResult.failed(ResultCode.FAILED, "用户名或密码错误");
        }
        Map<String, Object> resultMap = new HashMap<>();
        StudentDto studentDto = studentService.queryStudentDtoByStudentId(jwtService.getUserNameFromToken(token));
        resultMap.put("token", token);
        resultMap.put("tokenHead", tokenHead);
        resultMap.put("tokenHeader", tokenHeader);
        resultMap.put("student", studentDto);
        return CommonResult.success(resultMap);
    }

    /**
     * 老师登录
     */
    @PostMapping("/teaLogin")
    public CommonResult<?> teaLogin(@RequestBody AuthVo authVo) {
        String token = jwtService.login(JwtConstant.TEA_LOGIN_TYPE, authVo.getUsername(), authVo.getPassword());
        if (ObjectUtils.isEmpty(token)) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, Object> resultMap = new HashMap<>();
        TeacherDto teacherDto = teacherService.queryTeacherDtoByJobId(jwtService.getUserNameFromToken(token));
        resultMap.put("token", token);
        resultMap.put("tokenHead", tokenHead);
        resultMap.put("tokenHeader", tokenHeader);
        resultMap.put("teacher", teacherDto);
        return CommonResult.success(resultMap);
    }
}
