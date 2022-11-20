package com.zksy.reservationsystem.config;


import com.zksy.reservationsystem.interceptor.AuthInterceptor;
import com.zksy.reservationsystem.service.StudentService;
import com.zksy.reservationsystem.service.TeacherService;
import com.zksy.reservationsystem.util.auth.JwtTokenUtil;
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

    public MvcConfig(JwtTokenUtil jwtTokenUtil, StudentService studentService, TeacherService teacherService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(new AuthInterceptor(jwtTokenUtil, studentService, teacherService))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/v1/auth/**"
                ).order(1);
    }
}
