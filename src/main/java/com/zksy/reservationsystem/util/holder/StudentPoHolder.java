package com.zksy.reservationsystem.util.holder;


import com.zksy.reservationsystem.domain.po.StudentPo;

/**
 * 学生信息本地存储
 *
 * @author kkkoke
 * @since 2022/11/20
 */
public class StudentPoHolder {

    private static final ThreadLocal<StudentPo> tlStudentPo = new ThreadLocal<>();

    public static void saveStudentPo(StudentPo studentPo) {
        tlStudentPo.set(studentPo);
    }

    public static StudentPo getStudentPo() {
        return tlStudentPo.get();
    }

    public static void removeStudentPo() {
        tlStudentPo.remove();
    }
}
