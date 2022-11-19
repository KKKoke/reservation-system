package com.zksy.reservationsystem.util.holder;


import com.ihbut.checkinsystemnew.domain.po.StudentInfo;

public class StudentInfoHolder {

    private static final ThreadLocal<StudentInfo> tlStudentInfo = new ThreadLocal<>();

    public static void saveStudentInfo(StudentInfo studentInfo) {
        tlStudentInfo.set(studentInfo);
    }

    public static StudentInfo getStudentInfo() {
        return tlStudentInfo.get();
    }

    public static void removeStudentInfo() {
        tlStudentInfo.remove();
    }
}
