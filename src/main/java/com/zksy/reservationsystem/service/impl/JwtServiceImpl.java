package com.zksy.reservationsystem.service.impl;

import com.zksy.reservationsystem.common.ResultCode;
import com.zksy.reservationsystem.dao.StuAuthDao;
import com.zksy.reservationsystem.dao.TeaAuthDao;
import com.zksy.reservationsystem.domain.po.StuAuthPo;
import com.zksy.reservationsystem.domain.po.TeaAuthPo;
import com.zksy.reservationsystem.exception.BizException;
import com.zksy.reservationsystem.service.JwtService;
import com.zksy.reservationsystem.util.auth.JwtTokenUtil;
import com.zksy.reservationsystem.util.constant.JwtConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * JwtService 实现类
 *
 * @author kkkoke
 * @since 2022/9/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtTokenUtil jwtTokenUtil;

    private final StuAuthDao stuAuthDao;

    private final TeaAuthDao teaAuthDao;

    @Override
    public String login(Integer type, String username, String password) {
        String token = null;
        if (Objects.equals(type, JwtConstant.STU_LOGIN_TYPE)) {
            token = stuLogin(username, password);
        } else if (Objects.equals(type, JwtConstant.TEA_LOGIN_TYPE)) {
            token = teaLogin(username, password);
        }
        return token;
    }

    @Override
    public String refreshToken(String oldToken) {
        if (!jwtTokenUtil.isTokenExpired(oldToken.split(" ")[1])) {
            return jwtTokenUtil.refreshHeadToken(oldToken);
        }
        return null;
    }

    @Override
    public String stuLogin(String stuUsername, String stuPassword) {
        String token = null;
        try {
            StuAuthPo stuAuthPo = stuAuthDao.queryStuAuthPoByStudentId(stuUsername);
            // 比对用户的密码是否一致
            if (ObjectUtils.isEmpty(stuAuthPo) || !Objects.equals(stuAuthPo.getPasswd(), stuPassword)) {
                throw new BizException(ResultCode.FAILED, "用户名或密码错误");
            }
            token = jwtTokenUtil.generateToken(stuAuthPo.getUname(), JwtConstant.STU_LOGIN_TYPE);
        } catch (BizException e) {
            log.warn("{} - Login exception: {}", stuUsername, e.getMessage());
        }
        return token;
    }

    @Override
    public String teaLogin(String teaUsername, String teaPassword) {
        String token = null;
        try {
            TeaAuthPo teaAuthPo = teaAuthDao.queryTeaAuthPoByJobId(teaUsername);
            // 比对用户的密码是否一致
            if (ObjectUtils.isEmpty(teaAuthPo) || !Objects.equals(teaAuthPo.getPasswd(), teaPassword)) {
                throw new BizException(ResultCode.FAILED, "用户名或密码错误");
            }
            token = jwtTokenUtil.generateToken(teaAuthPo.getUname(), JwtConstant.TEA_LOGIN_TYPE);
        } catch (BizException e) {
            log.warn("{} - Login exception: {}", teaUsername, e.getMessage());
        }
        return token;
    }

    @Override
    public String getUserNameFromToken(String token) {
        return jwtTokenUtil.getUserNameFromToken(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return jwtTokenUtil.isTokenExpired(token);
    }

    @Override
    public String getClaimTypeFromToken(String token) {
        return jwtTokenUtil.getClaimTypeFromToken(token);
    }
}
