package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.StuAuthDao;
import com.zksy.reservationsystem.dao.StudentDao;
import com.zksy.reservationsystem.domain.dto.StudentDto;
import com.zksy.reservationsystem.domain.po.StudentPo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 学生服务层实现类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    private final StuAuthDao stuAuthDao;

    @Override
    public StudentDto queryStudentDtoByStudentId(String studentId) {
        StudentDto studentDto = studentDao.queryStudentDtoByStudentId(studentId);
        if (ObjectUtils.isEmpty(studentDto)) {
            throw new BizException(ResultCode.FAILED, "此学生不存在");
        }
        return studentDto;
    }

    @Override
    public StudentPo queryStudentPoByStudentId(String studentId) {
        StudentPo studentPo = studentDao.queryStudentPoByStudentId(studentId);
        if (ObjectUtils.isEmpty(studentPo)) {
            throw new BizException(ResultCode.FAILED, "此学生不存在");
        }
        return studentPo;
    }

    @Override
    public Boolean insertStudent(String name, String studentId, String password, String contact) {
        if (!ObjectUtils.isEmpty(studentDao.queryStudentPoByStudentId(studentId))) {
            throw new BizException(ResultCode.FAILED, "该学生已存在");
        }
        return studentDao.insertStudent(name, studentId, contact) && stuAuthDao.insertStuAuth(studentId, DigestUtils.md5DigestAsHex(password.getBytes()));
    }

    @Override
    public Boolean deleteStudent(String studentId) {
        if (ObjectUtils.isEmpty(studentDao.queryStudentPoByStudentId(studentId))) {
            throw new BizException(ResultCode.FAILED, "该学生不存在");
        }
        return studentDao.deleteStudent(studentId) && stuAuthDao.deleteStuAuth(studentId);
    }

    @Override
    public List<StudentDto> queryStudentDtoList() {
        return studentDao.queryStudentDtoList();
    }
}
