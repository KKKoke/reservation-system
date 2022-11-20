package com.zksy.reservationsystem.config;


import com.zksy.reservationsystem.interceptor.AuthInterceptor;
import com.zksy.reservationsystem.service.StudentService;
import com.zksy.reservationsystem.service.TeacherService;
import com.zksy.reservationsystem.util.auth.JwtTokenUtil;
import com.zksy.reservationsystem.util.constant.JwtConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置（拦截器配置）
 *
 * @author kkkoke
 * @since 2022/9/17
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final JwtTokenUtil jwtTokenUtil;

    private final StudentService studentService;

    private final TeacherService teacherService;

    private final JwtConstant jwtConstant;

    public MvcConfig(JwtTokenUtil jwtTokenUtil, StudentService studentService, TeacherService teacherService, JwtConstant jwtConstant) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.jwtConstant = jwtConstant;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(new AuthInterceptor(jwtTokenUtil, studentService, teacherService, jwtConstant))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/v1/auth/**"
                ).order(1);
    }
}
