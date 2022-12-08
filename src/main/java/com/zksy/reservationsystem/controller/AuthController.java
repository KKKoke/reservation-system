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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 微信登录
     */
    @PostMapping("/wechatLogin")
    public CommonResult<?> wechatLogin(@NotNull(message = "type can not be null") Integer type,
                                       @NotBlank(message = "code can not be null") String code) {
        Map<String, Object> jwtMap = jwtService.wechatLogin(type, code);
        if (ObjectUtils.isEmpty(jwtMap)) {
            return CommonResult.validateFailed("微信授权登录失败");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", jwtMap.get("token"));
        resultMap.put("tokenHead", tokenHead);
        resultMap.put("tokenHeader", tokenHeader);
        if (Objects.equals(type, JwtConstant.TEA_LOGIN_TYPE)) {
            resultMap.put("teacher", jwtMap.get("teacher"));
        } else if (Objects.equals(type, JwtConstant.STU_LOGIN_TYPE)) {
            resultMap.put("student", jwtMap.get("student"));
        }
        return CommonResult.success(resultMap);
    }

    /**
     * 刷新 token
     */
    @PostMapping("/refreshToken")
    public CommonResult<?> refresh(@RequestHeader(value = "${jwt.tokenHeader}") String tokenAuthString) {
        return CommonResult.success(jwtService.refreshToken(tokenAuthString), "refresh success");
    }

    /**
     * 判断 token 是否过期
     */
    @PostMapping("/isTokenExpired")
    public CommonResult<?> isTokenExpired(@RequestHeader(value = "${jwt.tokenHeader}") String tokenAuthString) {
        String[] auths = tokenAuthString.split(" ");
        boolean tokenExpired = jwtService.isTokenExpired(auths[1]);
        if (tokenExpired) {
            return CommonResult.unauthorized();
        }
        return CommonResult.success("token未过期");
    }

    /**
     * 绑定微信
     */
    @PostMapping("/boundWithWechat")
    public CommonResult<?> boundWithWechat(@RequestHeader(value = "${jwt.tokenHeader}") String tokenAuthString,
                                           @NotBlank(message = "code can not be null") String code) {
        String token = tokenAuthString.split(" ")[1];
        String type = jwtService.getClaimTypeFromToken(token);
        String uname = jwtService.getUserNameFromToken(token);
        if (jwtService.boundWithWechat(uname, Integer.valueOf(type), code)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 解除微信绑定
     */
    @PostMapping("/unboundWithWechat")
    public CommonResult<?> unboundWithWechat(@RequestHeader(value = "${jwt.tokenHeader}") String tokenAuthString) {
        String token = tokenAuthString.split(" ")[1];
        String type = jwtService.getClaimTypeFromToken(token);
        String uname = jwtService.getUserNameFromToken(token);
        if (jwtService.unboundWithWechat(uname, Integer.valueOf(type))) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
