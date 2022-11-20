package com.zksy.reservationsystem.util.aop;

import cn.hutool.core.util.ArrayUtil;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.util.holder.StudentPoHolder;
import com.zksy.reservationsystem.util.holder.TeacherPoHolder;
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

@Aspect
@Component
@Slf4j
public class AuthCheckAop {

    @Before("@annotation(com.zksy.reservationsystem.util.annotation.AuthStudent)")
    public void authCheckForStu(JoinPoint joinPoint) {
        boolean authStu = isAuthStudent((ProceedingJoinPoint) joinPoint);
        if (!authStu) {
            throw new BizException(ResultCode.FORBIDDEN, "非法请求，该接口只允许学生访问");
        }
    }

    @Before("@annotation(com.zksy.reservationsystem.util.annotation.AuthTeacher)")
    public void authCheckForAdmin(JoinPoint joinPoint) {
        boolean authAdmin = isAuthTeacher((ProceedingJoinPoint) joinPoint);
        if (!authAdmin) {
            throw new BizException(ResultCode.FORBIDDEN, "非法请求，该接口只允许老师访问");
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
    private boolean isAuthStudent(ProceedingJoinPoint point) {
        return !ObjectUtils.isEmpty(StudentPoHolder.getStudentPo());
    }

    /**
     * 判断是否是老师
     */
    private boolean isAuthTeacher(ProceedingJoinPoint point) {
        return !ObjectUtils.isEmpty(TeacherPoHolder.getTeacherPo());
    }
}
