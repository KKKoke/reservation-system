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
     * 通过 wxOpenId 获取老师认证信息
     */
    TeaAuthPo queryTeaAuthPoByWxOpenId(String wxOpenId);

    /**
     * 新增老师认证信息
     */
    Boolean insertTeaAuth(String uname, String passwd);

    /**
     * 删除老师认证信息
     */
    Boolean deleteTeaAuth(String jobId);

    /**
     * 修改老师认证状态
     */
    Boolean updateTeaAuth(TeaAuthPo teaAuthPo);

    /**
     * 获取老师认证信息包括已删除
     */
    TeaAuthPo queryTeaAuthPoWithDeleted(String jobId);

    /**
     * 绑定微信
     */
    Boolean boundWithWechat(String uname, String wxOpenId);

    /**
     * 解除微信绑定
     */
    Boolean unboundWithWechat(String uname);

    /**
     * 通过用户名获取 openid
     */
    String queryWxOpenIdByUname(String uname);
}
