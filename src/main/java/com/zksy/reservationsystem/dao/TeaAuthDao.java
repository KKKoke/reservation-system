package com.zksy.reservationsystem.dao;

import com.zksy.reservationsystem.domain.po.TeaAuthPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 老师认证数据库连接类
 *
 * @author kkkoke
 * @since 2022/11/20
 */
@Mapper
public interface TeaAuthDao {

    /**
     * 通过工号获取老师认证信息
     */
    TeaAuthPo queryTeaAuthPoByJobId(String jobId);

    /**
     * 新增老师认证信息
     */
    Boolean insertTeaAuth(String uname, String passwd);

    /**
     * 删除老师认证信息
     */
    Boolean deleteTeaAuth(String jobId);
}
