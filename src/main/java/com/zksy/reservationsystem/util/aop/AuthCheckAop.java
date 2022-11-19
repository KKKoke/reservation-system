package com.zksy.reservationsystem.util.aop;

import cn.hutool.core.util.ArrayUtil;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class AuthCheckAop {

    @Before("@annotation(com.ihbut.checkinsystemnew.utils.annotation.AuthCheckForStu)")
    public void authCheckForStu(JoinPoint joinPoint) {
        boolean authStu = isAuthStu((ProceedingJoinPoint) joinPoint);
        if (!authStu) {
            throw new BizException(ResultCode.FORBIDDEN, "非法请求，该接口只允许学生访问");
        }
    }

    @Before("@annotation(com.ihbut.checkinsystemnew.utils.annotation.AuthCheckForAdmin)")
    public void authCheckForAdmin(JoinPoint joinPoint) {
        boolean authAdmin = isAuthAdmin((ProceedingJoinPoint) joinPoint);
        if (!authAdmin) {
            throw new BizException(ResultCode.FORBIDDEN, "非法请求，该接口只允许管理员访问");
        }
    }

    @Before("@annotation(com.ihbut.checkinsystemnew.utils.annotation.AuthCheckForAdminOfSchoolLevel)")
    public void authCheckForAdminOfSchoolLevel(JoinPoint joinPoint) {
        boolean authAdminOfSchoolLevel = isAdminOfSchoolLevel((ProceedingJoinPoint) joinPoint);
        if (!authAdminOfSchoolLevel) {
            throw new BizException(ResultCode.FORBIDDEN, "非法请求，该接口只允许校级管理员访问");
        }
    }

    @Before("@annotation(com.ihbut.checkinsystemnew.utils.annotation.AuthCheckForAdminOfCollegeLevel)")
    public void authCheckForAdminOfCollegeLevel(JoinPoint joinPoint) {
        boolean authAdminOfCollegeLevel = isAdminOfCollegeLevel((ProceedingJoinPoint) joinPoint);
        if (!authAdminOfCollegeLevel) {
            throw new BizException(ResultCode.FORBIDDEN, "非法请求，该接口只允许院级管理员访问");
        }
    }

    @Before("@annotation(com.ihbut.checkinsystemnew.utils.annotation.AuthCheckForAdminOfClassLevel)")
    public void authCheckForAdminOfClassLevel(JoinPoint joinPoint) {
        boolean authAdminOfClassLevel = isAdminOfClassLevel((ProceedingJoinPoint) joinPoint);
        if (!authAdminOfClassLevel) {
            throw new BizException(ResultCode.FORBIDDEN, "非法请求，该接口只允许班级管理员访问");
        }
    }

    /**
     * 获取方法参数名和参数值
     *
     * @param joinPoint 点
     */
    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    /**
     * 判断是否是学生
     */
    private boolean isAuthStu(ProceedingJoinPoint point) {
        return !ObjectUtils.isEmpty(StudentInfoHolder.getStudentInfo());
    }

    /**
     * 判断是否是管理员
     */
    private boolean isAuthAdmin(ProceedingJoinPoint point) {
        return !ObjectUtils.isEmpty(AdminInfoHolder.getAdminInfo());
    }

    /**
     * 判断是否是校级管理员
     */
    private boolean isAdminOfSchoolLevel(ProceedingJoinPoint point) {
        return Objects.equals(AdminInfoHolder.getAdminInfo().getManageGrade(), 1);
    }

    /**
     * 判断是否是院级管理员
     */
    private boolean isAdminOfCollegeLevel(ProceedingJoinPoint point) {
        return Objects.equals(AdminInfoHolder.getAdminInfo().getManageGrade(), 2);
    }

    /**
     * 判断是否是班级管理员
     */
    private boolean isAdminOfClassLevel(ProceedingJoinPoint point) {
        return Objects.equals(AdminInfoHolder.getAdminInfo().getManageGrade(), 3);
    }
}
