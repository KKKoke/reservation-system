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
}
