package com.zksy.reservationsystem.util.holder;

import com.zksy.reservationsystem.domain.po.TeacherPo;

/**
 * 老师信息本地存储
 *
 * @author kkkoke
 * @since 2022/11/20
 */
public class TeacherPoHolder {

    private static final ThreadLocal<TeacherPo> tlTeacherPo = new ThreadLocal<>();

    public static void saveTeacherPo(TeacherPo teacherPo) {
        tlTeacherPo.set(teacherPo);
    }

    public static TeacherPo getTeacherPo() {
        return tlTeacherPo.get();
    }

    public static void removeTeacherPo() {
        tlTeacherPo.remove();
    }
}
