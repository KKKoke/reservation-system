package com.zksy.reservationsystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.StuAuthDao;
import com.zksy.reservationsystem.dao.StudentDao;
import com.zksy.reservationsystem.domain.dto.StudentDto;
import com.zksy.reservationsystem.domain.po.StudentPo;
import com.zksy.reservationsystem.domain.vo.StudentVo;
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
    public Boolean insertStudent(StudentVo studentVo) {
        if (!ObjectUtils.isEmpty(studentDao.queryStudentPoByStudentId(studentVo.getStudentId()))) {
            throw new BizException(ResultCode.FAILED, "该学生已存在");
        }
        return studentDao.insertStudent(BeanUtil.copyProperties(studentVo, StudentPo.class))
                && stuAuthDao.insertStuAuth(studentVo.getStudentId(), DigestUtils.md5DigestAsHex(studentVo.getPassword().getBytes()));
    }

    @Override
    public Boolean deleteStudent(String studentId) {
        if (ObjectUtils.isEmpty(studentDao.queryStudentPoByStudentId(studentId))) {
            throw new BizException(ResultCode.FAILED, "该学生不存在");
        }
        return studentDao.deleteStudent(studentId) && stuAuthDao.deleteStuAuth(studentId);
    }

    @Override
    public List<StudentDto> queryStudentDtoList(String name, String studentId, String className, String dormitory) {
        return studentDao.queryStudentDtoList(name, studentId, className, dormitory);
    }
}
