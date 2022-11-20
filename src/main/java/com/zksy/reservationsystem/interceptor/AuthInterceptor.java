package com.zksy.reservationsystem.interceptor;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.domain.po.StudentPo;
import com.zksy.reservationsystem.domain.po.TeacherPo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.StudentService;
import com.zksy.reservationsystem.service.TeacherService;
import com.zksy.reservationsystem.util.auth.JwtTokenUtil;
import com.zksy.reservationsystem.util.constant.JwtConstant;
import com.zksy.reservationsystem.util.holder.StudentPoHolder;
import com.zksy.reservationsystem.util.holder.TeacherPoHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenUtil jwtTokenUtil;
    private final StudentService studentService;
    private final TeacherService teacherService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    public AuthInterceptor(JwtTokenUtil jwtTokenUtil, StudentService studentService, TeacherService teacherService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    /**
     * 1.获取token
     * 2.查询用户
     * 3.放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader(tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
            if (jwtTokenUtil.validateToken(authToken)) {
                String username = jwtTokenUtil.getUserNameFromToken(authToken);
                String type = jwtTokenUtil.getClaimTypeFromToken(authToken); // 获取登录类型
                log.info("checking username:{}", username);
                if (username != null) {
                    if (Objects.equals(type, String.valueOf(JwtConstant.STU_LOGIN_TYPE))) {
                        StudentPo studentPo = studentService.queryStudentPoByStudentId(username);
                        StudentPoHolder.saveStudentPo(studentPo);
                    } else {
                        TeacherPo teacherPo = teacherService.queryTeacherPoByJobId(username);
                        TeacherPoHolder.saveTeacherPo(teacherPo);
                    }
                    log.info("{} - authenticated user: {}", username, username);
                }
            } else {
                throw new BizException(ResultCode.UNAUTHORIZED);
            }
        } else {
            throw new BizException(ResultCode.FAILED, "非法token格式");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        StudentPoHolder.removeStudentPo();
        TeacherPoHolder.removeTeacherPo();
    }
}
